package io.ecelik.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static io.ecelik.server.enumeration.Status.SERVER_UP;

import java.util.Arrays;

import io.ecelik.server.model.Server;
import io.ecelik.server.repo.ServerRepo;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
//	@Bean
//	CommandLineRunner run(ServerRepo serverRepo) {
//		return args -> {
//			serverRepo.save(new Server(null, "192.168.1.161", "Ubuntu Linux1", "18 GB", "Personal PC1",
//					"http://localhost:8080/server/image/server2.png", SERVER_UP));
//		};
//	}
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", 
				"Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorizzation", "Origin, Accept", 
				"X-Requested-With", "Access-Control-Request-Method", 
				"Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", 
				"Accept", "Authorization", "Access-Control-Allow-Origin", 
				"Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
