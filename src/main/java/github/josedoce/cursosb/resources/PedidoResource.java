package github.josedoce.cursosb.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import github.josedoce.cursosb.domain.Pedido;
import github.josedoce.cursosb.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> exibir(@PathVariable Integer id){
		return ResponseEntity.ok().body(pedidoService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido pedido){
		pedido = pedidoService.criar(pedido);
		//uri será visivel no header
		var uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pedido.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
