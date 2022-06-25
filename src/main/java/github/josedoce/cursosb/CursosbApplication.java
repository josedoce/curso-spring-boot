package github.josedoce.cursosb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.domain.Cidade;
import github.josedoce.cursosb.domain.Estado;
import github.josedoce.cursosb.domain.Produto;
import github.josedoce.cursosb.repositories.CategoriaRepository;
import github.josedoce.cursosb.repositories.CidadeRepository;
import github.josedoce.cursosb.repositories.EstadoRepository;
import github.josedoce.cursosb.repositories.ProdutoRepository;

@SpringBootApplication
public class CursosbApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired 
	private EstadoRepository estadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursosbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//CATEGORIAS
		var cat1 = new Categoria(null, "Informática");
		var cat2 = new Categoria(null, "Escritório");
		
		//PRODUTOS
		var prod1 = new Produto(null, "Computador", 2000.00);
		var prod2 = new Produto(null, "Impressora", 800.00);
		var prod3 = new Produto(null, "Mouse", 80.00);
		
		//RELACIONANDO CATEGORIA COM PRODUTO
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		//RELACIONANDO PRODUTO COM CATEGORIA
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		
		var est1 = new Estado(null, "Pernambuco");
		var est2 = new Estado(null, "São Paulo");
		
		//RELACIONANDO CIDADE COM ESTADO
		var cid1 = new Cidade(null, "Recife", est1);
		var cid2 = new Cidade(null, "São Paulo", est2);
		var cid3 = new Cidade(null, "Campinas", est2);
		
		//RELACIONANADO ESTADO COM CIDADES
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
	}
	
	
}
