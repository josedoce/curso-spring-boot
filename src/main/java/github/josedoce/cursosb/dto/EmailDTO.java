package github.josedoce.cursosb.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class EmailDTO {
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
}
