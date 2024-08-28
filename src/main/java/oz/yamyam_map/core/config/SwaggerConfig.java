package oz.yamyam_map.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	private static final String BEARER_TOKEN_PREFIX = "bearer";
	private static String securityJwtName = "JWT";

	@Bean
	public OpenAPI openAPI() {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
		return new OpenAPI()
			.addServersItem(new Server().url("/").description("현재 서버"))
			.components(apiComponents())
			.addSecurityItem(securityRequirement)
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("Yamyam Map Backend API Docs")
			.description("위치 기반 맛집 추천 웹 서비스")
			.version("1.0.0");
	}

	private Components apiComponents() {
		return new Components()
			.addSecuritySchemes(securityJwtName, new SecurityScheme()
				.name(securityJwtName)
				.type(SecurityScheme.Type.HTTP)
				.scheme(BEARER_TOKEN_PREFIX)
				.bearerFormat(securityJwtName));
	}
}