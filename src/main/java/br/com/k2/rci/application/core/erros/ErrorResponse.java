package br.com.k2.rci.application.core.erros;

import java.util.List;

import static java.util.Collections.emptyList;

public record ErrorResponse(String message,
                            Long timestamp,
                            Integer status,
                            String url,
                            List<ErrorField> errorFields) {

    public ErrorResponse(String message, Long timestamp, Integer status, String url) {
        this(message, timestamp, status, url, emptyList());
    }

}
