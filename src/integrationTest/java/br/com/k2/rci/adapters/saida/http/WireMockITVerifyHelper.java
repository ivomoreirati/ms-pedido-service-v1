package br.com.k2.rci.adapters.saida.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static br.com.k2.rci.adapters.saida.http.WireMockITHelper.toFormUrlEncoded;
import static br.com.k2.rci.adapters.saida.http.WireMockITHelper.toJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockITVerifyHelper {

    private static final String CONTENT_TYPE = HttpHeaders.CONTENT_TYPE;

    public static void verifyGet(String uri) {
        verifyGet(uri, List.of(), List.of());
    }

    public static void verifyGet(String uri, List<ChaveValorObjectHelper> headers) {
        verifyGet(uri, List.of(), headers);
    }

    public static void verifyGet(String uri,
                                 List<ChaveValorObjectHelper> queryParams,
                                 List<ChaveValorObjectHelper> headers) {

        var requestPatternBuilder = getRequestedFor(urlEqualTo(montarUri(uri, queryParams)));
        headers.forEach(h -> requestPatternBuilder.withHeader(h.chave(), containing(h.valor())));

        verify(requestPatternBuilder);

    }

    private static String montarUri(String uri, List<ChaveValorObjectHelper> queryParams) {
        var uriBuilder = UriComponentsBuilder
                .fromPath(uri);

        queryParams.forEach(c -> uriBuilder.queryParam(c.chave(), c.valor()));
        return uriBuilder.toUriString();
    }

    public static <T> void verifyPost(T request,
                                      String uri) {

        verifyPost(request, uri, List.of(), List.of());
    }

    public static <T> void verifyPost(T request,
                                      String uri,
                                      List<ChaveValorObjectHelper> headers) {

        verifyPost(request, uri, List.of(), headers);
    }

    public static <T> void verifyPost(T request,
                                      String uri,
                                      List<ChaveValorObjectHelper> queryParams,
                                      List<ChaveValorObjectHelper> headers) {

        var requestPatternBuilder = postRequestedFor(urlEqualTo(montarUri(uri, queryParams)));

        headers.forEach(h -> requestPatternBuilder.withHeader(h.chave(), containing(h.valor())));
        requestPatternBuilder.withRequestBody(equalTo(toJson(request)));

        verify(requestPatternBuilder);
    }

    public static void verifyPostMultiValueMap(MultiValueMap<String, String> request,
                                               String uri) {

        verifyPostMultiValueMap(request, uri, List.of(), List.of());
    }

    public static void verifyPostMultiValueMap(MultiValueMap<String, String> request,
                                               String uri,
                                               List<ChaveValorObjectHelper> queryParams,
                                               List<ChaveValorObjectHelper> headers) {

        var requestPatternBuilder = postRequestedFor(urlEqualTo(montarUri(uri, queryParams)))
                .withHeader(CONTENT_TYPE, containing(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        headers.forEach(h -> requestPatternBuilder.withHeader(h.chave(), containing(h.valor())));
        requestPatternBuilder.withRequestBody(equalTo(toFormUrlEncoded(request)));

        verify(requestPatternBuilder);

    }

    public static <T> void verifyPut(T request,
                                     String uri,
                                     List<ChaveValorObjectHelper> headers) {

        verifyPut(request, uri, List.of(), headers);
    }

    public static <T> void verifyPut(T request,
                                     String uri,
                                     List<ChaveValorObjectHelper> queryParams,
                                     List<ChaveValorObjectHelper> headers) {

        var requestPatternBuilder = putRequestedFor(urlEqualTo(montarUri(uri, queryParams)));

        headers.forEach(h -> requestPatternBuilder.withHeader(h.chave(), containing(h.valor())));
        requestPatternBuilder.withRequestBody(equalTo(toJson(request)));

        verify(requestPatternBuilder);
    }
}
