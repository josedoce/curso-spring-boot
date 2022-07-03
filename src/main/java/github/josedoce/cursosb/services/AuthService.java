package github.josedoce.cursosb.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		var cliente = clienteRepo.findByEmail(email)
				.orElseThrow(()-> new ObjectNotFoundException("Email não encontrado"));
		
		
		var novaSenha = novaSenha();
		cliente.setSenha(pe.encode(novaSenha));
		clienteRepo.save(cliente);
		emailService.sendNovaSenhaPorEmail(cliente, novaSenha);
	}
	private String novaSenha() {
		char[] vet = new char[10];
		for(int i =0;i<10;i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}
	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		}else if(opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		}else {
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
