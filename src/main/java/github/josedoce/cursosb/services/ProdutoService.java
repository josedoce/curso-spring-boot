package github.josedoce.cursosb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.Produto;
import github.josedoce.cursosb.repositories.CategoriaRepository;
import github.josedoce.cursosb.repositories.ProdutoRepository;
import github.josedoce.cursosb.resources.exception.ValueExceededException;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepo;
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Produto exibir(Integer id) {
		return produtoRepo.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Produto.class.getName()));
	}
	
	//busca paginada.
	public Page<Produto> pesquisar(String nome, List<Integer> ids, Integer pagina, Integer limite, String ordenarPor, String direcao) {
		if(limite <= 0 || limite > 120) {
			throw new ValueExceededException("Valor da query [limite] deve estar entre 1 e 120 itens por pagina.");
		}
		direcao = direcao.toUpperCase();
		var pageRequest = PageRequest.of(pagina, limite, Direction.valueOf(direcao) , ordenarPor);
		var categorias = categoriaRepo.findAllById(ids);
		return produtoRepo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
