package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dao.CupomDAO;
import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.CompraLivro;
import br.com.fatec.readmorebookstore.dominio.Cupom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidadorQuantidadeLivro implements IStrategy{

    @Override
    public String processar(AbstractEntidade entidade) {

        CompraLivro compraLivro = (CompraLivro) entidade;
        if(compraLivro.getLivro().getEstoque() < compraLivro.getQuantidade()){
            return "A quantidade selecionada não deve ser maior que a quantidade em estoque";
        }
        return null;
    }
}
