package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.CompraLivro;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
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
