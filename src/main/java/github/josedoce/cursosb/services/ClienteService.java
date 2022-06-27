package github.josedoce.cursosb.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.dto.CategoriaDTO;
import github.josedoce.cursosb.dto.ClienteDTO;
import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.resources.exception.DataIntegrityException;
import github.josedoce.cursosb.resources.exception.ValueExceededException;
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
	
	public Cliente criar(Cliente cliente) {
		cliente.setId(null);
		clienteRepo.save(cliente);
		return null;
	}
	
	public void editar(Integer id, ClienteDTO clienteDTO) {
		var hasCliente = clienteRepo.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
		hasCliente.setNome(clienteDTO.getNome());
		hasCliente.setEmail(clienteDTO.getEmail());
		try {
			clienteRepo.save(hasCliente);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivél editar.");
		}
	}

	public void deletar(Integer id) {
		var hasCategoria = clienteRepo.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
		try {
			clienteRepo.deleteById(hasCategoria.getId());			
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivél excluir um cliente com entidades relacionadas.");
		}
	}

	public Page<ClienteDTO> listar(Integer pagina, Integer limite, String ordenarPor, String direcao) {
		if(limite <= 0 || limite > 120) {
			throw new ValueExceededException("Valor da query [limite] deve estar entre 1 e 120 itens por pagina.");
		}
		
		direcao = direcao.toUpperCase();
		return clienteRepo.findAll(PageRequest.of(pagina, limite, Direction.valueOf(direcao) , ordenarPor))
				.map(e->new ClienteDTO(e));
	}
}
