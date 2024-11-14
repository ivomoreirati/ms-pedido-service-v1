package br.com.k2.rci.adapters.entrada.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.tracing.Tracer;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.mock;

@Getter
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(WebTestBase.Config.class)
public abstract class WebTestBase {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .defaultResponseCharacterEncoding(UTF_8)
            .build();
    }

    protected static String asJsonString(final Object obj) {
        try {
            return createObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> T stringToObject(String json, Class<T> tClazz) {
        try {
            return createObjectMapper().readValue(json, tClazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> List<T> stringToListObject(String json, Class<T> tClazz) {
        var mapper = createObjectMapper();
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, tClazz));
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

    @TestConfiguration
    static class Config {

        @Bean
        public Tracer tracer() {
            return mock(Tracer.class);
        }

    }

}
