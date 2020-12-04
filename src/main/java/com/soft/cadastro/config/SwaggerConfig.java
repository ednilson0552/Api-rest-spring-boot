package com.soft.cadastro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.soft.cadastro"))
          .paths(PathSelectors.regex("/clientes.*"))
          .build()
          .apiInfo(metaInfo());
    }    

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API de Clientes")
                .description("Um exemplo de aplicação Spring Boot REST API")
                .version("1.0.0")
                .contact(new Contact("Ednilson Furtado","https://github.com/ednilson0552/Api-rest-spring-boot.git","ednilson.furtado@gmail.com"))
                .build();
    }
}
