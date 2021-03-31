package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.CompraLivro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraLivroDAO extends CrudRepository<CompraLivro, Integer>{
}
