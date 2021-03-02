package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dao.CidadeDAO;
import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Cidade;
import br.com.fatec.readmorebookstore.dominio.Endereco;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ComplementarCidade implements IStrategy {

    @Autowired
    private CidadeDAO  cidadeDAO;

    @Override
    public String processar(AbstractEntidade entidade) {
        if (entidade instanceof Endereco) {
            Endereco endereco = (Endereco) entidade;
            Cidade cidade = cidadeDAO.findCidadeByNome(endereco.getNomeCidade());
            endereco.setCidade(cidade);
        } else {
            return "Entidade: "+entidade.getClass().getCanonicalName()+" não é um endereço!";
        }
        return null;
    }
}
