package github.josedoce.cursosb.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.josedoce.cursosb.domain.Produto;
import github.josedoce.cursosb.dto.ProdutoDTO;
import github.josedoce.cursosb.resources.utils.Url;
import github.josedoce.cursosb.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> exibir(@PathVariable Integer id){
		return ResponseEntity.ok().body(produtoService.exibir(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> listar(
			@RequestParam(defaultValue = "") String nome,
			@RequestParam(defaultValue = "") String categorias,
			@RequestParam(defaultValue = "0") Integer pagina, 
			@RequestParam(defaultValue = "24") Integer limite,
			@RequestParam(defaultValue = "id") String ordenarPor,
			@RequestParam(defaultValue = "ASC") String direcao) {
		var ids = Url
				.decode(categorias)
				.toIntegerList();
		var nomeDecodificado = Url
				.decode(nome)
				.toDecodedParam();
		var produtos = produtoService.pesquisar(nomeDecodificado, ids, pagina, limite, ordenarPor, direcao)
				.map((e)->new ProdutoDTO(e));
		return ResponseEntity.ok().body(produtos);
	}
}
