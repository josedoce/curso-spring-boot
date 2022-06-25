package github.josedoce.cursosb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import github.josedoce.cursosb.domain.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
	/**
	 * Não precisa criar o repository das subclasses...
	 * A classe pai abrange todas as filhas.
	 * */
}
