package github.josedoce.cursosb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import github.josedoce.cursosb.services.EmailService;
import github.josedoce.cursosb.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
    @Bean
    EmailService emailService() {
        return new SmtpEmailService();
    }
}
