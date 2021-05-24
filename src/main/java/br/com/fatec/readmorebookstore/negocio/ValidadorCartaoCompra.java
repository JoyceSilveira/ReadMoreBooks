package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Cartao;
import org.springframework.stereotype.Service;

@Service
public class ValidadorCartaoCompra implements IStrategy{

    @Override
    public String processar(AbstractEntidade entidade) {
        Cartao cartao = (Cartao) entidade;
        if(cartao.getNumero() == "0000000000000000"){
            return "Cartão inválido";
        }
        return null;
    }
}
