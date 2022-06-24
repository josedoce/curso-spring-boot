package github.josedoce.cursosb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.josedoce.cursosb.domain.Produto;

@Repository
public interface ProdutoRepository 
extends JpaRepository<Produto, Integer>{

}
