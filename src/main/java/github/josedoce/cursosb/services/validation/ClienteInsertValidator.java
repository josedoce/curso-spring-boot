package github.josedoce.cursosb.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import github.josedoce.cursosb.domain.enums.TipoCliente;
import github.josedoce.cursosb.dto.ClienteCompletoDTO;
import github.josedoce.cursosb.resources.exception.FieldMessage;
import github.josedoce.cursosb.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteCompletoDTO>{

	@Override
	public boolean isValid(ClienteCompletoDTO c, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		 
		if(c.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(c.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(c.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(c.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
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
