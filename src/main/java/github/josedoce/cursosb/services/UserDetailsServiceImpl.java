package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		var cliente = clienteRepo.findByEmail(email)
				.orElseThrow(()->new UsernameNotFoundException(email));
		
		return new UserSpringSecurity(
			cliente.getId(),
			cliente.getEmail(),
			cliente.getSenha(),
			cliente.getPerfis()
		);
	}
}
