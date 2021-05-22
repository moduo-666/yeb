package com.xxx.server.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置类
 * @author Wu Zicong
 * @create 2021-04-24 12:59
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xxx.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts()    )
                .securitySchemes(securitySchemes());
    }
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("yeb接口文档")
                .description("yeb接口文档")
                .contact(new Contact("xxx","http:localhost:8081/doc.html","xxx@xxx.com"))
                .version("1.0")
                .build();
    }
    private List<ApiKey> securitySchemes(){
        List<ApiKey> list = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        list.add(apiKey);
        return list;
    }
    private List<SecurityContext> securityContexts(){
        //设置需要登录认证的路径
        List<SecurityContext> list =new ArrayList<>();
        list.add(getContextByPath("/hello/.*"));
        return list;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        list.add(new SecurityReference("Authorzation",authorizationScopes));
        return list;
    }
}


