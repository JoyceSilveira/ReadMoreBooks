package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.EnderecoCliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoClienteDAO extends CrudRepository<EnderecoCliente, Integer> {
}
