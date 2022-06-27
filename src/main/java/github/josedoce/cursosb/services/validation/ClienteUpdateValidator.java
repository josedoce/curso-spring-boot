package github.josedoce.cursosb.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import github.josedoce.cursosb.dto.ClienteDTO;
import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(ClienteDTO c, ConstraintValidatorContext context) {
		//com isso, pegamos os dados da url.
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		List<FieldMessage> list = new ArrayList<>();
		 	
		var aux = clienteRepo.findByEmail(c.getEmail());
		if(aux.isPresent() && !aux.get().getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente."));
		}
		
		list.forEach(e-> {
			//esse erro será pego no ResourceExceptionHandler na forma de lista.
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName())
			.addConstraintViolation();
		});
		return list.isEmpty();
	}
	
}
