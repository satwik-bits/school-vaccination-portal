package com.school.vaccination.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI schoolVaccinationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School Vaccination Portal API Swagger")
                        .description("Backend API documentation for the School Vaccination Portal")
                        .version("2.0.0")
                        .contact(new Contact()
                                .name("Satwik Mohanty")
                                .email("satwik0810@gmail.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project GitHub Repository")
                        .url("https://github.com/satwik-bits/school-vaccination-portal"));
    }

}
