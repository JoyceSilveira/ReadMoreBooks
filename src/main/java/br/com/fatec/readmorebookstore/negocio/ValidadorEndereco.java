package br.com.fatec.readmorebookstore.negocio;


import br.com.fatec.readmorebookstore.dominio.*;
import org.springframework.stereotype.Service;

@Service
public class ValidadorEndereco implements IStrategy {

	@Override
	public String processar(AbstractEntidade entidade) {
		if (entidade instanceof Endereco) {

			Endereco endereco = (Endereco) entidade;
			String tipoResidencia = endereco.getTipoResidencia();
			String tipoLogradouro = endereco.getTipoLogradouro();
			String logradouro = endereco.getLogradouro();
			String numero = endereco.getNumero();
			String bairro = endereco.getBairro();
			String cep = endereco.getCep();
			String complemento = endereco.getComplemento();
			Cidade cidade = endereco.getCidade();
			Estado estado = null;
			if (cidade != null) {
				estado = cidade.getEstado();
			}


			if (tipoResidencia == null || tipoLogradouro == null || logradouro == null || numero == null
					|| bairro == null || cep == null || complemento == null || cidade == null || estado == null) {
				return "Tipo de residência, Tipo de logradouro, logradouro, número, Cidade e Estado são de preenchimento obrigatório!\n";
			} else if (tipoResidencia.trim().equals("") || tipoLogradouro.trim().equals("") || logradouro.trim().equals("")
					|| numero.trim().equals("") || bairro.trim().equals("") || cep.trim().equals("")
					|| complemento.trim().equals("")) {
				return "Tipo de residência, Tipo de logradouro, logradouro, número, Cidade e Estado são de preenchimento obrigatório!\n";
			}

		} else {
			return "Deve ser registrado um endereço!\n";
		}

		return null;
	}

}
