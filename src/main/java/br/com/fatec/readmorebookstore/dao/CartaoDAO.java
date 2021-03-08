package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Cartao;
import br.com.fatec.readmorebookstore.dominio.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoDAO extends CrudRepository<Cartao, Integer> {


    Optional<List<Cartao>> findAllByCliente(Cliente cliente);
}
