package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.LogEstoque;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEstoqueDAO extends CrudRepository<LogEstoque, Integer> {
}
