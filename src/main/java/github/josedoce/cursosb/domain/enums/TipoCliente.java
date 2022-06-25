package github.josedoce.cursosb.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"), 
	PESSOAJURIDICA(2 ,"Pessoa Jurídica");

	private int cod;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.cod = codigo;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null)
			return null;
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod()))
				return x;
		}
		throw new IllegalArgumentException("Código invalido: "+cod);
	}
}
