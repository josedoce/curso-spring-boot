package github.josedoce.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.repositories.CategoriaRepository;
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
}
