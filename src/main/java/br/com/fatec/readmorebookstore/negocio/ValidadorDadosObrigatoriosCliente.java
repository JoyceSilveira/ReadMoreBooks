package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Cliente;
import br.com.fatec.readmorebookstore.dominio.Endereco;
import br.com.fatec.readmorebookstore.dominio.TipoEnderecoEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidadorDadosObrigatoriosCliente implements IStrategy {

	@Override
	public String processar(AbstractEntidade entidade) {
		
		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;

			String genero = cliente.getGenero();
			String nome = cliente.getNome();
			String dataNascimento = cliente.getDataNascimento();
			String cpf = cliente.getCpf();
			String telefone = cliente.getTelefone();
			String email = cliente.getEmail();
			String senha = cliente.getSenha();
			List<Endereco> enderecos = cliente.getEnderecos();


			StringBuilder sb = new StringBuilder();
			if (genero == null || nome == null || dataNascimento == null || cpf == null
					|| telefone == null || email == null || senha == null) {
				sb.append("Gênero, Data de nascimento, Nome, CPF, telefone, email, senha são de preenchimento obrigatório!\n");
			} else if (genero.trim().equals("") || nome.trim().equals("") || cpf.trim().equals("")
					|| telefone.trim().equals("") || email.trim().equals("") || senha.trim().equals("")){
				sb.append("Gênero, Data de nascimento, Nome, CPF, telefone, email, senha são de preenchimento obrigatório!\n");
			}

			sb.append(validarEnderecosObrigatorios(enderecos));

			for (Endereco endereco : enderecos) {
				ValidadorEndereco vEnd = new ValidadorEndereco();
				String msgEnd = vEnd.processar(endereco);
				if (msgEnd != null) {
					sb.append(msgEnd);

				}
			}

			if (sb.length() > 0) {
				return sb.toString();
			} else {
				return null;
			}
			
		} else {
			return "Deve ser registrado um cliente!";
		}
	}

	private String validarEnderecosObrigatorios(List<Endereco> enderecos) {
		StringBuilder msgRetorno = new StringBuilder();
		boolean existeEnderecoResidencial = false;
		boolean existeEnderecoCobranca = false;
		boolean existeEnderecoEntrega = false;
		for (Endereco endereco : enderecos) {
			if (endereco != null && endereco.getTipoEndereco() != null) {
				existeEnderecoResidencial = endereco.getTipoEndereco().equals(TipoEnderecoEnum.RESIDENCIAL);
				existeEnderecoCobranca = endereco.getTipoEndereco().equals(TipoEnderecoEnum.COBRANCA);
				existeEnderecoEntrega = endereco.getTipoEndereco().equals(TipoEnderecoEnum.ENTREGA);
			}
		}
		if (!existeEnderecoResidencial) {
			msgRetorno.append("O preenchimento do endereço residencial é obrigatório\n");
		}
		if (!existeEnderecoCobranca) {
			msgRetorno.append("O preenchimento do endereço cobrança é obrigatório\n");
		}
		if (!existeEnderecoEntrega) {
			msgRetorno.append("O preenchimento do endereço entrega é obrigatório\n");
		}
		return msgRetorno.toString();
	}

}
