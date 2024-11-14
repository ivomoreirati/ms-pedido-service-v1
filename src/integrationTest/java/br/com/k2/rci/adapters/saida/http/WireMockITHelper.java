package br.com.k2.rci.adapters.saida.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.util.Iterator;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockITHelper {

    public static <T> void wiremockGet(String url, T response) {
        wiremockGet(url, response, List.of());
    }

    public static <T> void wiremockGet(String url, T response, HttpStatus status) {
        wiremockGet(url, response, status, List.of());
    }

    public static <T> void wiremockGet(String url, T response, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = get(urlPathEqualTo(url));

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, null, httpHeaders);
    }

    public static <T> void wiremockGet(String url, T response, HttpStatus status, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = get(urlPathEqualTo(url));

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, status, httpHeaders);
    }

    public static void wiremockGetArrayByte(String url, byte[] response) {
        wiremockGetArrayByte(url, response, List.of());
    }

    public static void wiremockGetArrayByte(String url, byte[] response, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = get(urlPathEqualTo(url));
        stubArrayByte(mappingBuilder, queryParams, response);
    }

    public static <T, E> void wiremockPost(String url, E response) {
        wiremockPost(url, response, List.of());
    }

    public static <T, E> void wiremockPost(String url, E response, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = post(urlPathEqualTo(url));

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, null, httpHeaders);
    }

    public static <T, E> void wiremockPost(String url, T request, E response) {
        wiremockPost(url, request, response, HttpStatus.OK, List.of());
    }

    public static <T, E> void wiremockPost(String url, T request, E response, HttpStatus status, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = mappingBuilderPost(url, request);

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, status, httpHeaders);
    }

    public static <E> void wiremockPatch(String url, E response) {
        wiremockPatch(url, response, List.of());
    }

    public static <E> void wiremockPatch(String url, E response, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = patch(urlPathEqualTo(url));

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, null, httpHeaders);
    }

    public static <E> void wiremockPut(String url, E response) {
        wiremockPut(url, response, null, List.of());
    }

    public static <E> void wiremockPut(String url, HttpStatus status, E response) {
        wiremockPut(url, response, status, List.of());
    }

    public static <E> void wiremockPut(String url, E response, HttpStatus status, List<ChaveValorObjectHelper> queryParams) {
        MappingBuilder mappingBuilder = put(urlPathEqualTo(url));

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, status, httpHeaders);
    }

    public static <K, V, E> void wiremockPostWithMultiValueMap(String url, MultiValueMap<K, V> request, E response) {
        wiremockPostWithMultiValueMap(url, request, response, null, List.of());
    }

    public static <K, V, E> void wiremockPostWithMultiValueMap(String url, MultiValueMap<K, V> request, E response, HttpStatus status) {
        wiremockPostWithMultiValueMap(url, request, response, status, List.of());
    }

    public static <K, V, E> void wiremockPostWithMultiValueMap(String url,
                                                               MultiValueMap<K, V> request,
                                                               E response,
                                                               HttpStatus status,
                                                               List<ChaveValorObjectHelper> queryParams) {

        MappingBuilder mappingBuilder = mappingBuilderPostWithMap(url, request);

        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        stub(mappingBuilder, queryParams, response, status, httpHeaders);
    }

    private static void stubArrayByte(MappingBuilder mappingBuilder, List<ChaveValorObjectHelper> queryParams, byte[] response) {

        queryParams.forEach(param -> mappingBuilder.withQueryParam(param.chave(), equalTo(param.valor())));

        mappingBuilder.willReturn(aResponse()
                .withStatus(200)
                .withBody(response)
                .withHeader(HttpHeaders.CONNECTION, "close")
        );

        stubFor(mappingBuilder);
    }

    private static <T> void stub(MappingBuilder mappingBuilder,
                                 List<ChaveValorObjectHelper> queryParams,
                                 T response,
                                 HttpStatus status,
                                 HttpHeaders httpHeadersResponse) {

        queryParams.forEach(param -> mappingBuilder.withQueryParam(param.chave(), equalTo(param.valor())));

        var responseDefinitionBuilder = aResponse();
        addHttpHeaders(responseDefinitionBuilder, httpHeadersResponse);

        var httpStatus = status == null ? HttpStatus.OK.value() : status.value();
        responseDefinitionBuilder.withStatus(httpStatus).withHeader(HttpHeaders.CONNECTION, "close").withBody(toJson(response));

        mappingBuilder.willReturn(responseDefinitionBuilder);

        stubFor(mappingBuilder);

    }

    private static <K, V> MappingBuilder mappingBuilderPostWithMap(String url, MultiValueMap<K, V> request) {
        return post(urlPathEqualTo(url))
                .withRequestBody(equalTo(toFormUrlEncoded(request)));
    }

    private static <T> MappingBuilder mappingBuilderPost(String url, T request) {
        return post(urlPathEqualTo(url))
                .withRequestBody(equalToJson(toJson(request), true, true));
    }

    public static <T> String toJson(T object) {
        try {
            return createObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T stringToObject(String json, Class<T> tClazz) {
        try {
            return createObjectMapper().readValue(json, tClazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static <K, V> String toFormUrlEncoded(MultiValueMap<K, V> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            K key = it.next();
            V value = map.getFirst(key);
            appendFormUrlEncoded(key, value, sb);
            if (it.hasNext()) {
                sb.append('&');
            }
        }
        return sb.toString();
    }

    private static <K, V> void appendFormUrlEncoded(K key, V value, StringBuilder sb) {
        sb.append(key).append('=');
        if (value != null) {
            sb.append(value);
        }
    }

    private static void addHttpHeaders(ResponseDefinitionBuilder builder, HttpHeaders headers) {
        headers.forEach((key, values) -> {
            for (String value : values) {
                builder.withHeader(key, value);
            }
        });
    }

}

