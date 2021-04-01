package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.CompraCartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraCartaoDAO extends CrudRepository<CompraCartao, Integer>{
}
