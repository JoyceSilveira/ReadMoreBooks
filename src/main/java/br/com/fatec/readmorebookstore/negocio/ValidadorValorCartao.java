package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dao.CupomDAO;
import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Compra;
import br.com.fatec.readmorebookstore.dominio.Cupom;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ValidadorValorCartao implements IStrategy {

    @Override
    public String processar(AbstractEntidade entidade) {
        Compra compra = (Compra) entidade;
        if(compra.getCartoes().size() > 1){
            if (compra.getValorTotal() / compra.getCartoes().size() < 10.0){
                return "Limite de menos de R$ 10,00 em cada cartão atingido.";
            }
        }
        return null;
    }
}
