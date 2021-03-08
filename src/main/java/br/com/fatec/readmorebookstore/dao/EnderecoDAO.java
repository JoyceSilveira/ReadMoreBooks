package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Cliente;
import br.com.fatec.readmorebookstore.dominio.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoDAO extends CrudRepository<Endereco, Integer> {

    Optional<List<Endereco>> findAllByCliente(Cliente cliente);
}
