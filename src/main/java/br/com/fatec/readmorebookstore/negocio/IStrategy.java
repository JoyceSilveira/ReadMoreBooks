package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;

public interface IStrategy {

    String processar(AbstractEntidade entidade);
}
