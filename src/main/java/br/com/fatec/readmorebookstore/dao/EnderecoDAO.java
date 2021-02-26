package br.com.fatec.readmorebookstore.dao;

import br.com.fatec.readmorebookstore.dominio.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoDAO extends CrudRepository<Endereco, Integer> {
}
