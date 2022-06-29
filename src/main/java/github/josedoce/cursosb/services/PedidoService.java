package github.josedoce.cursosb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import github.josedoce.cursosb.domain.ItemPedido;
import github.josedoce.cursosb.domain.PagamentoComBoleto;
import github.josedoce.cursosb.domain.Pedido;
import github.josedoce.cursosb.domain.enums.EstadoPagamento;
import github.josedoce.cursosb.repositories.ItemPedidoRepository;
import github.josedoce.cursosb.repositories.PagamentoRepository;
import github.josedoce.cursosb.repositories.PedidoRepository;
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
		System.out.println(pedido);
		return pedido;
	}
}
