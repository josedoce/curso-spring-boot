package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.dto.CategoriaDTO;
import github.josedoce.cursosb.repositories.CategoriaRepository;
import github.josedoce.cursosb.resources.exception.DataIntegrityException;
import github.josedoce.cursosb.resources.exception.ValueExceededException;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Categoria buscar(Integer id) {
		return categoriaRepo.
				findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
		
	}

	public Categoria criar(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepo.save(categoria);
	}
	
	public Categoria editar(Integer id, CategoriaDTO categoriaDTO) {
		var hasCategoria = categoriaRepo.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
		hasCategoria.setId(hasCategoria.getId());
		hasCategoria.setNome(categoriaDTO.getNome());
		return categoriaRepo.save(hasCategoria);
	}

	public void deletar(Integer id) {
		var hasCategoria = categoriaRepo.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
		try {
			categoriaRepo.deleteById(hasCategoria.getId());			
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivél excluir uma categoria que possui produtos.");
		}
	}

	//https://www.baeldung.com/spring-data-jpa-pagination-sorting
	public Page<CategoriaDTO> listar(Integer pagina, Integer limite, String ordenarPor, String direcao) {
		if(limite <= 0 || limite > 120) {
			throw new ValueExceededException("Valor da query [limite] deve estar entre 1 e 120 itens por pagina.");
		}
		
		direcao = direcao.toUpperCase();
		return categoriaRepo.findAll(PageRequest.of(pagina, limite, Direction.valueOf(direcao) , ordenarPor))
				.map(e->new CategoriaDTO(e));
	}
}
