package github.josedoce.cursosb.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.dto.ClienteCompletoDTO;
import github.josedoce.cursosb.dto.ClienteDTO;
import github.josedoce.cursosb.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<Page<ClienteDTO>> listar(
			@RequestParam(defaultValue = "0") Integer pagina, 
			@RequestParam(defaultValue = "24") Integer limite,
			@RequestParam(defaultValue = "id") String ordenarPor,
			@RequestParam(defaultValue = "ASC") String direcao) {
		return ResponseEntity.ok()
				.body(clienteService.listar(pagina, limite, ordenarPor, direcao));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> exibir(@PathVariable Integer id) {
		return ResponseEntity.ok().body(clienteService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteCompletoDTO clienteCompletoDTO){
		var cliente = clienteService.criar(clienteCompletoDTO);
		var uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO){
		clienteService.editar(id, clienteDTO);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
}
