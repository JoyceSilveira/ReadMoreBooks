package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.CartaoCliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoClienteDAO extends CrudRepository<CartaoCliente, Integer> {
}
