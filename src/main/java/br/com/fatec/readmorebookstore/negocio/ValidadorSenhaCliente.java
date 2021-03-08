package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ValidadorSenhaCliente implements IStrategy {

    @Override
    public String processar(AbstractEntidade entidade) {
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            String senha = cliente.getSenha();
            String confirmacaoSenha = cliente.getSenhaConfirmacao();
            if (!senha.equals(confirmacaoSenha)) {
                return "As senhas devem ser idênticas";
            }
        }
        return null;
    }
}
