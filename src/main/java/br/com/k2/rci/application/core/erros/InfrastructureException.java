package br.com.k2.rci.application.core.erros;

public class InfrastructureException extends RuntimeException {

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable ex) {
        super(message, ex);
    }
}
