package com.learnToday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    
   private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));

    private ApiInfo apiInfo() {
        return new ApiInfo("Learn Today Rest API",
                "APIs for LearnToday",
                "",
                "",
                new Contact("test", "www.org.com", "karanamguruteja@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
    	ParameterBuilder parameterBuilder = new ParameterBuilder();
    	parameterBuilder.name(AUTHORIZATION_HEADER).modelRef(new ModelRef("string")).parameterType("header").description("JWT token").required(true).build();
    	List<Parameter> parameters = new ArrayList<>();
    	parameters.add(parameterBuilder.build());
    	List<SecurityScheme> schemeList = new ArrayList<>();
    	schemeList.add(apiKey());
    	return new Docket(DocumentationType.SWAGGER_2).produces(DEFAULT_PRODUCES_CONSUMES).consumes(DEFAULT_PRODUCES_CONSUMES)
                .ignoredParameterTypes(Authentication.class)
                .securitySchemes(schemeList)
                .useDefaultResponseMessages(false)
    			.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(input ->
                	!PathSelectors.regex("/autheniticate.*").apply(input) &&
                	PathSelectors.any().apply(input))
                .build().globalOperationParameters(parameters);
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "JWT", "header");
    }

  

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}
