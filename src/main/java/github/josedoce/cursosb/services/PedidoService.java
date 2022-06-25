package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.domain.Pedido;
import github.josedoce.cursosb.repositories.PedidoRepository;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		return pedidoRepository.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
	}
}
