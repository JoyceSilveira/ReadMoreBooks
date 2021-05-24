package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.GrupoPrecificacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoPrecificacaoDAO extends CrudRepository<GrupoPrecificacao, Integer> {
}
