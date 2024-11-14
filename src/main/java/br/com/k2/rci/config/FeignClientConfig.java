package br.com.k2.rci.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("br.com.tlf.pco3")
public class FeignClientConfig { }
