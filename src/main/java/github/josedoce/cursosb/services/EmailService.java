package github.josedoce.cursosb.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import github.josedoce.cursosb.domain.Pedido;

public interface EmailService {
	void enviarEmailDeConfirmacaoDePedido(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	
	void enviarEmailHtmlDeConfirmacaoDePedido(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
}
