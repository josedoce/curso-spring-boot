package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.repositories.CategoriaRepository;
import github.josedoce.cursosb.resources.exception.DataIntegrityException;
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
	
	public Categoria editar(Integer id, Categoria categoria) {
		var hasCategoria = categoriaRepo.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
		categoria.setId(hasCategoria.getId());
		
		return categoriaRepo.save(categoria);
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
}
