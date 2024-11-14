package br.com.k2.rci.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@Component
@Order(HIGHEST_PRECEDENCE + 3)
class RequestResponseLogFilter implements FilterPagamentoObrigacaoService {

    private static final String REQUEST = "Request: {}";
    private static final String RESPONSE = "Response: {}";
    private static final String METHOD = "method";
    private static final String URI = "uri";
    private static final String QUERY_STRING = "queryString";

    private final String filterIgnoreUrls;
    private final Boolean printRequestResponse;
    private final ObjectMapper objectMapper;

    RequestResponseLogFilter(@Value("${filter.ignore.urls}") String filterIgnoreUrls,
                             @Value("${filter.request-response.logs.enabled:false}") Boolean printRequestResponse,
                             ObjectMapper objectMapper) {

        this.filterIgnoreUrls = filterIgnoreUrls;
        this.printRequestResponse = printRequestResponse;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        var uri = ((HttpServletRequest) request).getRequestURI();

        if (ehUriSemPermissao(uri, filterIgnoreUrls) || Boolean.FALSE.equals(printRequestResponse)) {
            chain.doFilter(request, response);
            return;
        }

        var requestWrapper = requestWrapper(request);
        var responseWrapper = responseWrapper(response);

        chain.doFilter(requestWrapper, responseWrapper);

        logRequest(requestWrapper);
        logResponse(responseWrapper);

    }

    private void logRequest(ContentCachingRequestWrapper request)  {

        var bodyString = new String(request.getContentAsByteArray());
        var bodyJson = getBodyJson(bodyString);
        var headers = headersToMapValuesRequest(request);
        var parameters = request.getParameterMap();
        var metadata = getMetadataRequest(request);
        var logObject = new LogObjectRequest(headers, parameters, bodyJson, metadata);
        var logObjectJson = objectMapper.valueToTree(logObject);

        log.info(REQUEST, logObjectJson);
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {

        var bodyString = new String(response.getContentAsByteArray());
        var bodyJson = getBodyJson(bodyString);
        var logObject = new LogObjectResponse(headersToMapValuesResponse(response), bodyJson);
        var logObjectJson = objectMapper.valueToTree(logObject);

        log.info(RESPONSE, logObjectJson);

        response.copyBodyToResponse();
    }

    private Map<String, String> headersToMapValuesRequest(ContentCachingRequestWrapper request) {

        var headerNames = Collections.list(request.getHeaderNames());
        var headers = new HashMap<String, String>();

        headerNames.forEach(headerName -> headers.put(headerName, request.getHeader(headerName).replace("\"", "")));

        return headers;
    }

    private Map<String, String> getMetadataRequest(ContentCachingRequestWrapper request) {

        var metadata = new HashMap<String, String>();
        metadata.put(METHOD, request.getMethod());
        metadata.put(URI, request.getRequestURI());
        metadata.put(QUERY_STRING, request.getQueryString());

        return metadata;
    }

    private Map<String, String> headersToMapValuesResponse(ContentCachingResponseWrapper response) {

        var headers = new HashMap<String, String>();
        var headerNames = response.getHeaderNames();

        headerNames.forEach(headerName -> headers.put(headerName, response.getHeader(headerName)));

        return headers;
    }

    private static LinkedHashMap<String, Object> getBodyJson(String bodyString) {

        if (bodyString.isBlank()) {
            return new LinkedHashMap<>();
        }

        var jsonParser = new JSONParser(bodyString);

        try {
            return jsonParser.parseObject();
        } catch (ParseException ex) {
            return new LinkedHashMap<>();
        }

    }

    private ContentCachingRequestWrapper requestWrapper(ServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper requestWrapper) {
            return requestWrapper;
        }
        return new ContentCachingRequestWrapper((HttpServletRequest) request);
    }

    private ContentCachingResponseWrapper responseWrapper(ServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            return responseWrapper;
        }
        return new ContentCachingResponseWrapper((HttpServletResponse) response);
    }

    record LogObjectRequest(Map<String, String> headers,
                            Map<String, String[]> parameters,
                            LinkedHashMap<String, Object> body,
                            Map<String, String> metadata) {
    }

    record LogObjectResponse(Map<String, String> headers,
                            LinkedHashMap<String, Object> body) {
    }

}
