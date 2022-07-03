package github.josedoce.cursosb.services;

import org.springframework.security.core.context.SecurityContextHolder;

import github.josedoce.cursosb.security.UserSpringSecurity;

public class UserService {
	public static UserSpringSecurity authenticated() {
		try {
			//para obter um usuário logado, usamos o SecurityContextHolder
			return (UserSpringSecurity) SecurityContextHolder
					.getContext()
					.getAuthentication()
					.getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
}
