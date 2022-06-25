package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepo;
	
	public Cliente buscar(Integer id) {
		return clienteRepo.
				findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
		
	}
}
