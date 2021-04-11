package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dao.CupomDAO;
import br.com.fatec.readmorebookstore.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorItemExistenteCarrinho implements IStrategy {

    @Override
    public String processar(AbstractEntidade entidade) {
        CompraLivro compraLivro = (CompraLivro) entidade;
        Cliente cliente = compraLivro.getCliente();
        for(int i = 0; i < cliente.getItensVinculados().size(); i++){
            if(cliente.getItensVinculados().get(i).getLivro().getId() == compraLivro.getLivro().getId()){
                return "O livro já se encontra no carrinho.";
            }
        }
        return null;
    }
}
