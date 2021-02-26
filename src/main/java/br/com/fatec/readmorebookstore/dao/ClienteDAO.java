package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDAO extends CrudRepository<Cliente, Integer> {
}
