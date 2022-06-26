package github.josedoce.cursosb;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import github.josedoce.cursosb.domain.Categoria;
import github.josedoce.cursosb.domain.Cidade;
import github.josedoce.cursosb.domain.Cliente;
import github.josedoce.cursosb.domain.Endereco;
import github.josedoce.cursosb.domain.Estado;
import github.josedoce.cursosb.domain.ItemPedido;
import github.josedoce.cursosb.domain.PagamentoComBoleto;
import github.josedoce.cursosb.domain.PagamentoComCartao;
import github.josedoce.cursosb.domain.Pedido;
import github.josedoce.cursosb.domain.Produto;
import github.josedoce.cursosb.domain.enums.EstadoPagamento;
import github.josedoce.cursosb.domain.enums.TipoCliente;
import github.josedoce.cursosb.repositories.CategoriaRepository;
import github.josedoce.cursosb.repositories.CidadeRepository;
import github.josedoce.cursosb.repositories.ClienteRepository;
import github.josedoce.cursosb.repositories.EnderecoRepository;
import github.josedoce.cursosb.repositories.EstadoRepository;
import github.josedoce.cursosb.repositories.ItemPedidoRepository;
import github.josedoce.cursosb.repositories.PagamentoRepository;
import github.josedoce.cursosb.repositories.PedidoRepository;
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
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursosbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//CATEGORIAS
		var cat1 = new Categoria(null, "Informática");
		var cat2 = new Categoria(null, "Escritório");
		var cat3 = new Categoria(null, "Cama mesa e Banho");
		var cat4 = new Categoria(null, "Eletrónicos");
		var cat5 = new Categoria(null, "Jardinagem");
		var cat6 = new Categoria(null, "Decoração");
		var cat7 = new Categoria(null, "Perfumaria");
		
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
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
		
		
		var cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "23224392899",TipoCliente.PESSOAFISICA);
		//RELACIONANDO CLIENTES COM TELEFONES
		cli1.getTelefones().addAll(Arrays.asList("74839929","28399902"));
		
		var end1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "39483204", cli1, cid1);
		var end2 = new Endereco(null, "Avenida Matos", "3105", "Sala 800", "Centro", "29382299", cli1, cid2);
		//RELACIONANDO CLIENTES COM ENDEREÇOS
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//pedidos
		var ped1 = new Pedido(null, sdf.parse("30/09/2022 13:07"), cli1, end1);
		var ped2 = new Pedido(null, sdf.parse("30/09/2022 14:07"), cli1, end2);
		
		//pagamento
		var cartao = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(cartao);
			
		var boleto = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("05/10/2022 14:07"), null);
		ped2.setPagamento(boleto);
		//RELACIONANDO CLIENTE COM PEDIDOS.
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(cartao, boleto));
	
		var ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.0);
		var ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.0);
		var ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.0);
		
		//RELACIONANDO PEDIDO A ITEM PEDIDO
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//RELACIONANDO PRODUTO A PEDIDO
		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	
	
}
