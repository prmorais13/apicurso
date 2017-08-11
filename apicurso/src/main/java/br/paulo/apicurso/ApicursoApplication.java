package br.paulo.apicurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.paulo.apicurso.config.property.ApiCursoProperty;

@SpringBootApplication
@EnableConfigurationProperties(ApiCursoProperty.class)
public class ApicursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApicursoApplication.class, args);
	}
	
}
