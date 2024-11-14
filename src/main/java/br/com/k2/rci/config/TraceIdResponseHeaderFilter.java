package br.com.k2.rci.config;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE + 2)
class TraceIdResponseHeaderFilter implements FilterPagamentoObrigacaoService {

    private static final String TRACE_ID = "traceId";

    private final String filterIgnoreUrls;
    private final Tracer tracer;

    TraceIdResponseHeaderFilter(@Value("${filter.ignore.urls}") String filterIgnoreUrls,
                                Tracer tracer) {

        this.filterIgnoreUrls = filterIgnoreUrls;
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        var uri = ((HttpServletRequest) request).getRequestURI();

        if (ehUriSemPermissao(uri, filterIgnoreUrls)) {
            chain.doFilter(request, response);
            return;
        }

        var currentSpan = tracer.currentSpan();

        if (currentSpan != null) {
            var traceId = currentSpan.context().traceId();
            ((HttpServletResponse) response).setHeader(TRACE_ID, traceId);
        }

        chain.doFilter(request, response);
    }

}