package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cartao extends AbstractEntidade {
    private String numero;
    private String nomeImpresso;
    @Enumerated(EnumType.STRING)
    private BandeiraCartaoEnum bandeira;
    private Integer cvv;
    @ManyToOne
    private Cliente cliente;
}
