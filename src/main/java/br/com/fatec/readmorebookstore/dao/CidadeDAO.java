package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Cidade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeDAO extends CrudRepository<Cidade, Integer> {
}
