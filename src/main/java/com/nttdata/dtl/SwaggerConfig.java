package com.nttdata.dtl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author XNEEBT
 * @version 1.0
 * @since 10.05.2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    protected String getMappingBasePackage() {
        return "com.nttdata.dtl";
    }

    @Bean
    public Docket swaggerUI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getMappingBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Party Management API")
                .description("Implementation of Party Management API")
                .version("2.0")
                .build();
    }
}
