package github.josedoce.cursosb.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO {
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Size(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres.")
	private String nome;
	
	@NotEmpty(message="Email é obrigatório")
	@Email(message="Email é inválido.")
	private String email;
	
	public ClienteDTO() {}
	public ClienteDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		email = cliente.getEmail();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
