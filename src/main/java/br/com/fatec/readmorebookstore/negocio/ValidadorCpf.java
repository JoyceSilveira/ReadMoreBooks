package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ValidadorCpf implements IStrategy {

	@Override
	public String processar(AbstractEntidade entidade) {
		
		if (entidade instanceof Cliente) {
			Cliente a = (Cliente) entidade;
			
			if(a.getCpf().length() < 9){
				return "CPF deve conter 14 digitos!";
			}
			
		} else {
			return "CPF não pode ser válidado, pois entidade não é um cliente!";
		}
		return null;
	}

}
