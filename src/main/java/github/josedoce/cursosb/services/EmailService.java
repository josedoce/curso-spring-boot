package github.josedoce.cursosb.services;

import org.springframework.mail.SimpleMailMessage;

import github.josedoce.cursosb.domain.Pedido;

public interface EmailService {
	void enviarEmailDeConfirmacaoDePedido(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
}
