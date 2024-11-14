package br.com.k2.rci.application.core.erros;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
