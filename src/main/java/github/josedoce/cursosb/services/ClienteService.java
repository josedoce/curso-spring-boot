package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.domain.Endereco;
import github.josedoce.cursosb.domain.enums.Perfil;
import github.josedoce.cursosb.domain.enums.TipoCliente;
import github.josedoce.cursosb.dto.ClienteCompletoDTO;
import github.josedoce.cursosb.dto.ClienteDTO;
import github.josedoce.cursosb.repositories.CidadeRepository;
import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.repositories.EnderecoRepository;
import github.josedoce.cursosb.resources.exception.DataIntegrityException;
import github.josedoce.cursosb.resources.exception.ValueExceededException;
import github.josedoce.cursosb.security.UserSpringSecurity;
import github.josedoce.cursosb.services.exceptions.AuthorizationException;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private CidadeRepository cidadeRepo;
	@Autowired
	private EnderecoRepository enderecoRepo;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente buscar(Integer id) {
		
		UserSpringSecurity user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso proibido");
		}
		return clienteRepo.
				findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente criar(ClienteCompletoDTO clienteCompletoDTO) {
		var cliente = fromDTO(clienteCompletoDTO);
		cliente.setId(null);
		var clienteNovo = clienteRepo.save(cliente);
		enderecoRepo.saveAll(cliente.getEnderecos());
		return clienteNovo;
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
			throw new DataIntegrityException("Não é possivél excluir porque há pedidos relacionados.");
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
	public Cliente fromDTO(ClienteDTO c) {
		return new Cliente(c.getId(), c.getNome(), c.getEmail(), null, null, null);
	}
	public Cliente fromDTO(ClienteCompletoDTO c) {
		
		var cliente = new Cliente(null, c.getNome(), c.getEmail(), c.getCpfOuCnpj(), TipoCliente.toEnum(c.getTipo()), pe.encode(c.getSenha()));
		var cidade = cidadeRepo.findById(c.getCidadeId())
				.orElse(null);
		var endereco = new Endereco(null, c.getLogradouro(), c.getNumero(), c.getComplemento(), c.getBairro(), c.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(c.getTelefone1());
		if(c.getTelefone2() != null) {
			cliente.getTelefones().add(c.getTelefone2());
		}
		if(c.getTelefone3() != null) {
			cliente.getTelefones().add(c.getTelefone3());
		}
		return cliente;
		
	}
}
