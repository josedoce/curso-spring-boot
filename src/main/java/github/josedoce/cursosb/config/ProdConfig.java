package github.josedoce.cursosb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import github.josedoce.cursosb.services.EmailService;
import github.josedoce.cursosb.services.SmtpEmailService;

@Profile("prod")
@Configuration
public class ProdConfig {
	
	 @Bean
	 EmailService emailService() {
	     return new SmtpEmailService();
	 }
	
}
