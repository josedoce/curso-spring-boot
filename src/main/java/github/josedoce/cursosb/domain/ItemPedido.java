package github.josedoce.cursosb.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;
	// id.
	@JsonIgnore
	/**
	 * A partir de itemPedido, não serializo ne produto e nem pedido...
	 * Nesse caso ,não precisa definir anotação no outro lado.
	 * 
	 * 
	 **/
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {}

	public ItemPedido(Pedido pedido, Produto produdo, Double desconto, Integer quantidade, Double preco) {
		super();
		this.id.setPedido(pedido);
		this.id.setProdudo(produdo);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public Produto getProdudo() {
		return id.getProdudo();
	}
	
	public ItemPedidoPK getId() {
		return this.id;
	}
	
	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(id, other.id);
	}
	
}
