package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cupom extends AbstractEntidade{
    @ManyToOne(cascade= CascadeType.PERSIST)
    private Compra compra;
    @ManyToOne(cascade= CascadeType.PERSIST)
    private Cliente cliente;
    private double valor;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoCupomEnum tipoCupom;
}
