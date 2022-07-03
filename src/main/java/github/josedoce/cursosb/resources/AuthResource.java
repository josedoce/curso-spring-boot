package github.josedoce.cursosb.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.josedoce.cursosb.dto.EmailDTO;
import github.josedoce.cursosb.security.JWTUtil;
import github.josedoce.cursosb.security.UserSpringSecurity;
import github.josedoce.cursosb.services.AuthService;
import github.josedoce.cursosb.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthService authService;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> regreshToken(HttpServletResponse response){
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer "+token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO obj){
		authService.sendNewPassword(obj.getEmail());
		return ResponseEntity.noContent().build();
	}
}
