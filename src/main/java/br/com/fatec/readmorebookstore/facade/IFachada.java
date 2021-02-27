package br.com.fatec.readmorebookstore.facade;


import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;

import java.util.List;

public interface IFachada {

    String cadastrar(AbstractEntidade entidade);
    String excluir(AbstractEntidade entidade);
    String alterar(AbstractEntidade entidade);
    List<AbstractEntidade> consultar(AbstractEntidade entidade);

}
