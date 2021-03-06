package github.josedoce.cursosb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import github.josedoce.cursosb.domain.ItemPedido;
import github.josedoce.cursosb.domain.PagamentoComBoleto;
import github.josedoce.cursosb.domain.Pedido;
import github.josedoce.cursosb.domain.enums.EstadoPagamento;
import github.josedoce.cursosb.repositories.ItemPedidoRepository;
import github.josedoce.cursosb.repositories.PagamentoRepository;
import github.josedoce.cursosb.repositories.PedidoRepository;
import github.josedoce.cursosb.security.UserSpringSecurity;
import github.josedoce.cursosb.services.exceptions.AuthorizationException;
import github.josedoce.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepo;
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Integer id) {
		return pedidoRepository.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido criar(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.buscar(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			var pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamaentoComBoleto(pagto, pedido.getInstante());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepo.save(pedido.getPagamento());
		
		for(ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.exibir(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		itemPedidoRepo.saveAll(pedido.getItens());
		emailService.enviarEmailHtmlDeConfirmacaoDePedido(pedido);
		return pedido;
	}
	
	public Page<Pedido> listar(Integer pagina, Integer limite, String ordenarPor, String direcao) {
		UserSpringSecurity user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso proibido");
		}
		var cliente = clienteService.buscar(user.getId());
		
		direcao = direcao.toUpperCase();
		return pedidoRepository.findByCliente(cliente, PageRequest.of(pagina, limite, Direction.valueOf(direcao) , ordenarPor));
	}
}
