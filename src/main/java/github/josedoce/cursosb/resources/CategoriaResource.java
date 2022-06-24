package github.josedoce.cursosb.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@GetMapping("/")
	public String listar() {
		return "REST está funcionando.";
	}
}
