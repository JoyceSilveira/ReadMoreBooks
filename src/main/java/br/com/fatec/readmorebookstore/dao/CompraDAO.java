package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraDAO extends CrudRepository<Compra, Integer>{
}
