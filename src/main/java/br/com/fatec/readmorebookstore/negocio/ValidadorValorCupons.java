package br.com.fatec.readmorebookstore.negocio;

import br.com.fatec.readmorebookstore.dao.CupomDAO;
import br.com.fatec.readmorebookstore.dominio.AbstractEntidade;
import br.com.fatec.readmorebookstore.dominio.Compra;
import br.com.fatec.readmorebookstore.dominio.Cupom;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class ValidadorValorCupons implements IStrategy{
    @Autowired
    private CupomDAO cupomDAO;

    @Override
    public String processar(AbstractEntidade entidade) {
        Compra compra = (Compra) entidade;
        if(compra.getCupons().size() > 1){
            List<Double> desconto = new ArrayList<>();
            Double valorDesconto = 0.0;
            for(int i = 0; i < compra.getCupons().size(); i++){
                Cupom cupom = cupomDAO.findById(compra.getCupons().get(i)).orElse(null);
                desconto.add(cupom.getValor());
                valorDesconto += cupom.getValor();
            }
            if(valorDesconto > compra.getValorTotal()){
                Arrays.sort(new List[]{desconto});
                Double valorDescontado = compra.getValorTotal();
                for(int i = 0; i < desconto.size(); i++){
                    if(valorDescontado <= 0){
                        return "Cupons utilizados em excesso";
                    }
                    valorDescontado -= desconto.get(i);
                }
            }
        }
        return null;
    }

}
