package br.com.fatec.readmorebookstore.dao;


import br.com.fatec.readmorebookstore.dominio.Cupom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomDAO extends CrudRepository<Cupom, Integer> {
}
