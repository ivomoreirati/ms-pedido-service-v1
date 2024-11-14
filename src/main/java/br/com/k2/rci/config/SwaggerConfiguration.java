package br.com.k2.rci.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfiguration {

    @Bean
    OpenAPI openAPI(@Value("${version:latest}") String version) {
        return new OpenAPI().info(info(version));
    }

    private Info info(String version) {
        return new Info()
                .title("Serviço responsável pelo domínio de Pagamento do processum3")
                .version(version);
    }
}
