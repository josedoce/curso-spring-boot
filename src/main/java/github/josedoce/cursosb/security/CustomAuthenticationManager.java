package github.josedoce.cursosb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import github.josedoce.cursosb.services.UserDetailsServiceImpl;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
	//https://stackoverflow.com/questions/71281032/spring-security-exposing-authenticationmanager-without-websecurityconfigureradap
	
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	public CustomAuthenticationManager(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final UserDetails userDetail = userDetailsServiceImpl.loadUserByUsername(authentication.getName()); 
		var db_senha = userDetail.getPassword();
		var json_senha = authentication.getCredentials().toString();
		
		if(!passwordEncoder().matches(json_senha, db_senha)) {
			throw new BadCredentialsException("Senha errada, pai...");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
	}

}
