package github.josedoce.cursosb.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import github.josedoce.cursosb.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			var creds = new ObjectMapper()
				.readValue(request.getInputStream(), CredenciaisDTO.class);
			
			var authToken = new UsernamePasswordAuthenticationToken(
				creds.getEmail(), 
				creds.getSenha(),
				new ArrayList<>()
			);
			return authenticationManager.authenticate(authToken);
			
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	//os dados do metodo de cima virão para cá em caso de sucesso, ai poderemos trata-los
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		var username = authResult.getName();
		var token = jwtUtil.generateToken(username);
		response.setHeader("Authorization","Bearer "+ token);
	}
}
