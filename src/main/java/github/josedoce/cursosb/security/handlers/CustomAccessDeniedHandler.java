package github.josedoce.cursosb.security.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Map<String, String> payload = new HashMap<>();
		payload.put("erro", "Acesso proibido.");
		ObjectMapper obj = new ObjectMapper();
		
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response
		.getWriter()
		.append(obj.writeValueAsString(payload));

	}

}
