package github.josedoce.cursosb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.josedoce.cursosb.domain.Cidade;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
