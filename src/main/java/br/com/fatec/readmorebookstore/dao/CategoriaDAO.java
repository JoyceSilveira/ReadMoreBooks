package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDAO extends CrudRepository<Categoria, Integer>{
}
