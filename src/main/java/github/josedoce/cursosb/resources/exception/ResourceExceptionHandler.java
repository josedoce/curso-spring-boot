package github.josedoce.cursosb.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import github.josedoce.cursosb.services.exceptions.AuthorizationException;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		var err = new StandardError(); 
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setMsg(e.getMessage());
		err.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		var err = new StandardError(); 
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setMsg(e.getMessage());
		err.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataIntegrity(MethodArgumentNotValidException e, HttpServletRequest request){
		var err = new ValidationError(); 
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setMsg("Erro de validação.");
		err.setTimestamp(System.currentTimeMillis());
		
		e.getBindingResult()
		.getFieldErrors()
		.forEach(f->err.addError(f.getField(), f.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
	@ExceptionHandler(ValueExceededException.class)
	public ResponseEntity<StandardError> dataIntegrity(ValueExceededException e, HttpServletRequest request){
		var err = new StandardError(); 
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setMsg(e.getMessage());
		err.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<StandardError> badCredentials(BadCredentialsException e, HttpServletRequest request){
		var err = new StandardError(); 
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setMsg(e.getMessage());
		err.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
		var err = new StandardError(); 
		err.setStatus(HttpStatus.FORBIDDEN.value());
		err.setMsg(e.getMessage());
		err.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
