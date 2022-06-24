package github.josedoce.cursosb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.josedoce.cursosb.domain.Categoria;

@Repository
public interface CategoriaRepository
extends JpaRepository<Categoria, Integer>{

}
