package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Livro;
import org.springframework.stereotype.Service;

@Service
public class ValidadorLucroLivro implements IStrategy{

    @Override
    public String processar(AbstractEntidade entidade) {
        Livro livro = (Livro) entidade;
        if(livro.getPreco() < livro.getCusto() + livro.getCusto() * livro.getGrupoPrecificacao().getMargemLucro()){
            livro.setPreco(livro.getCusto() + livro.getCusto() * livro.getGrupoPrecificacao().getMargemLucro());
            return "O preço de venda é menor do que define o grupo de precificação";
        }
        return null;
    }
}
