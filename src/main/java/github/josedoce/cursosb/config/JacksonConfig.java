package github.josedoce.cursosb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import github.josedoce.cursosb.domain.PagamentoComBoleto;
import github.josedoce.cursosb.domain.PagamentoComCartao;

@Configuration
public class JacksonConfig {
	
	//esse bean ficará responsavel por configurar os tipos de instancia para o modelo Pagamento...
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder() {
			@Override
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			}
			
		};
	}
}
