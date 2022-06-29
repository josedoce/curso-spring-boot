package github.josedoce.cursosb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import github.josedoce.cursosb.services.EmailService;
import github.josedoce.cursosb.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
