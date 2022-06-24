package github.josedoce.cursosb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.repositories.CategoriaRepository;

@SpringBootApplication
public class CursosbApplication implements CommandLineRunner{
	@Autowired
	private CategoriaRepository categoriaRepository;
	public static void main(String[] args) {
		SpringApplication.run(CursosbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		var cat1 = new Categoria(null, "Informática");
		var cat2 = new Categoria(null, "Escritório");
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
	}
	
	
}
