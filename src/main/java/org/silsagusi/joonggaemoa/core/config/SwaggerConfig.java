package org.silsagusi.joonggaemoa.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.addSecurityItem(new SecurityRequirement().addList("AccessToken"))
			.components(new Components()
				.addSecuritySchemes(("AccessToken"), createAPIKeyScheme()))
			.info(apiInfo());
	}

	private io.swagger.v3.oas.models.info.Info apiInfo() {
		return new Info().title("JoonggaeMoa API")
			.description("JoonggaeMoa API Documentation")
			.version("v1.0");
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
			.bearerFormat("JWT")
			.scheme("bearer");
	}
}
