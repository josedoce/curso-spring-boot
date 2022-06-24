package github.josedoce.cursosb.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.josedoce.cursosb.domain.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@GetMapping("/")
	public List<Categoria> listar() {
		var cat1 = new Categoria(1, "Informática");
		var cat2 = new Categoria(2, "Escritório");
		
		List<Categoria> listaCategorias = new ArrayList<>();
		listaCategorias.add(cat1);
		listaCategorias.add(cat2);
		return listaCategorias;
	}
}
