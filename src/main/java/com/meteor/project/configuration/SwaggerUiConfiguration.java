package com.meteor.project.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerUiConfiguration {

	@Value("${developer.name}")
	private String developerName;

	@Value("${developer.contact.url}")
	private String developerContactUrl;

	@Value("${developer.contact.email-address}")
	private String developerEmailAddress;

	@Value("${spring.application.name}")
	private String springApplicationName;

	@Value("${spring.application.version}")
	private String springApplicationVersion;

	@Value("${spring.application.description}")
	private String springApplicationDescription;


	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.forCodeGeneration(Boolean.TRUE)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.meteor.project.api"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.metaData())
				.ignoredParameterTypes(ApiIgnore.class)
				.securitySchemes(Lists.newArrayList(apiKey()))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title(this.springApplicationName)
				.description(this.springApplicationDescription)
				.contact(new Contact(this.developerName, this.developerContactUrl, this.developerEmailAddress))
				.version(this.springApplicationVersion)
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(
				"global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("apiKey",
				authorizationScopes));
	}

}
