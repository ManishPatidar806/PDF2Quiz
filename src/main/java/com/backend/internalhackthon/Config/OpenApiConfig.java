package com.backend.internalhackthon.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(getInfo());
    }

    Info getInfo() {
        return new Info().title("Converter").version("1.0.1").
                description("It is helpful for Secure computer based examination")
                .contact(new Contact().name("SecuringSharing").url("url of this website"))
                .license(new License().name("MIT-LICENSE").url("URL of LICENSE"))
                .termsOfService("URL of Terms&Service");
    }


}