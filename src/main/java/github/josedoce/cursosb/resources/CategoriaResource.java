package github.josedoce.cursosb.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.dto.CategoriaDTO;
import github.josedoce.cursosb.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<Page<CategoriaDTO>> listar(
			@RequestParam(defaultValue = "0") Integer pagina, 
			@RequestParam(defaultValue = "24") Integer limite,
			@RequestParam(defaultValue = "id") String ordenarPor,
			@RequestParam(defaultValue = "ASC") String direcao) {
		return ResponseEntity.ok().body(categoriaService.listar(pagina, limite, ordenarPor, direcao));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> exibir(@PathVariable Integer id) {
		return ResponseEntity.ok().body(categoriaService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDTO){
		var categoria = categoriaService.criar(categoriaDTO.toCategoria());
		//uri será visivel no header
		var uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO){
		categoriaService.editar(id, categoriaDTO.toCategoria());
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		
		categoriaService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
