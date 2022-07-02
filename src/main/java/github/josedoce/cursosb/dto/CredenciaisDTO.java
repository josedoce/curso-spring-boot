package github.josedoce.cursosb.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;
	private String senha;
	public CredenciaisDTO(){}
	public CredenciaisDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}
	
	
}
