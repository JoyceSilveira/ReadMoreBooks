package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ComplementarDtCadastro implements IStrategy {

	@Override
	public String processar(AbstractEntidade entidade) {
		
		if (entidade != null) {
			LocalDate data = LocalDate.now();
			entidade.setDataCadastro(data);
		} else {
			return "Entidade: "+entidade.getClass().getCanonicalName()+" nula!";
		}
		
		
		
		return null;
	}

}
