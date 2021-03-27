package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Livro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroDAO extends CrudRepository<Livro, Integer> {
}
