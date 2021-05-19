package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.LogDesativacaoLivro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDesativacaoLivroDAO extends CrudRepository<LogDesativacaoLivro, Integer> {
}
