package br.com.k2.rci.config;

import br.com.k2.rci.application.core.erros.*;
import br.com.tlf.pco3.application.core.erros.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@RequiredArgsConstructor
class RestExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                  HttpServletRequest request) {

        return new ResponseEntity<>(construirErrorResponse(exception, request, NOT_FOUND), NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception,
                                                          HttpServletRequest request) {
        return new ResponseEntity<>(construirErrorResponse(exception, request, UNPROCESSABLE_ENTITY), UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponse> handleException(Exception exception,
                                                  HttpServletRequest request) {
        return new ResponseEntity<>(construirErrorResponse(exception, request, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException exception,
                                                                      HttpServletRequest request) {
        var headerName = format("Request Header '%s' ausente", exception.getHeaderName());

        var errorResponse = new ErrorResponse(headerName, System.currentTimeMillis(),
            BAD_REQUEST.value(), request.getRequestURI());

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        var errorFields = ex.getConstraintViolations().stream()
                .map(this::buildErrorFieldFromConstraintViolation)
                .toList();

        return new ResponseEntity<>(construirErrorResponse(request, BAD_REQUEST, errorFields), BAD_REQUEST);
    }

    private ErrorField buildErrorFieldFromConstraintViolation(ConstraintViolation<?> violation) {
        var propertiesPath = StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                .toList();

        var fieldName = String.valueOf(propertiesPath.get(propertiesPath.size() - 1));
        var message = violation.getMessage();

        return new ErrorField(fieldName, message);
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<Object> handleFeignStatusException(FeignException exception) {
        try {
            return ResponseEntity
                .status(exception.status())
                .body(objectMapper.readTree(exception.contentUTF8()));
        } catch (Exception e) {
            return ResponseEntity
                .status(exception.status())
                .body(exception.contentUTF8());
        }
    }

    @ExceptionHandler(InfrastructureException.class)
    ResponseEntity<ErrorResponse> handleInfrastructureException(InfrastructureException ex, HttpServletRequest request) {
        var httpStatus = INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(construirErrorResponse(ex, request, httpStatus), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;

        var errorFields = ex.getFieldErrors()
            .stream()
            .map(error -> new ErrorField(error.getField(), error.getDefaultMessage()))
            .toList();

        return new ResponseEntity<>(construirErrorResponse(request, httpStatus, errorFields), httpStatus);
    }

    private static ErrorResponse construirErrorResponse(Exception ex, HttpServletRequest request, HttpStatus httpStatus) {
        var mensagem = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        return new ErrorResponse(mensagem,
            System.currentTimeMillis(),
            httpStatus.value(),
            request.getRequestURI(),
            emptyList());
    }

    private static ErrorResponse construirErrorResponse(HttpServletRequest request,
                                                        HttpStatus httpStatus,
                                                        List<ErrorField> errorFieldList) {

        return new ErrorResponse("Erro de validação",
            System.currentTimeMillis(),
            httpStatus.value(),
            request.getRequestURI(),
            errorFieldList);
    }

}
