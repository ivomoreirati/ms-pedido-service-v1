package br.com.k2.rci.adapters.saida.http;

import br.com.k2.rci.config.FeignClientConfig;
import com.github.tomakehurst.wiremock.core.Options;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("http-integration")
@ContextConfiguration(classes = {FeignClientConfig.class, FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
public abstract class FeignClientBase {

}
