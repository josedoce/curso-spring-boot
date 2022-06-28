package github.josedoce.cursosb.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import github.josedoce.cursosb.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	//substitua por um serviço de emissão de boleto...
	public void preencherPagamaentoComBoleto(PagamentoComBoleto pagto, Date instante) {
		var cal = Calendar.getInstance();
		cal.setTime(instante);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());		
	}

}
