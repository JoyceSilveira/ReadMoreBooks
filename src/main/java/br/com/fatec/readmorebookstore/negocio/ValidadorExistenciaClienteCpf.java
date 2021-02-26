package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dao.ClienteDAO;
import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorExistenciaClienteCpf implements IStrategy {

    @Autowired
    private ClienteDAO clienteDAO;

    @Override
    public String processar(AbstractEntidade entidade) {
        String mensagemRetorno = "";
        if (entidade instanceof Cliente) {
            Cliente cliente = (Cliente) entidade;
            Cliente clienteExistente =  clienteDAO.findClienteByCpf(cliente.getCpf());
            if (clienteExistente != null) {
                mensagemRetorno = "Cliente não cadastrado pois este CPF já está sendo utilizado!";
            }
        } else {
            mensagemRetorno = "Não é possível validar pois a entidade não é um cliente";

        }
        return mensagemRetorno;
    }
}
