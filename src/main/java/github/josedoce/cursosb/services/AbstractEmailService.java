package github.josedoce.cursosb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import github.josedoce.cursosb.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void enviarEmailDeConfirmacaoDePedido(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailDePedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailDePedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
}
