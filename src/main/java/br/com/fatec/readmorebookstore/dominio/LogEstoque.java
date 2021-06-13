package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LogEstoque extends AbstractEntidade implements Comparable<LogEstoque>{
    @ManyToOne(cascade= CascadeType.PERSIST)
    private Livro livro;
    private Integer quantidade;
    private String fornecedor;
    private Double precoCusto;
    private String dataEntrada;

    @Override public int compareTo(LogEstoque outroLog) {
        if (this.getId() > outroLog.getId()) {
            return -1;
        } if (this.getId() < outroLog.getId()) {
            return 1;
        }
        return 0;
    }
}
