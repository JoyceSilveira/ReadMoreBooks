package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cartao extends AbstractEntidade {
    private String numero;
    private String nomeImpresso;
    private BandeiraCartaoEnum bandeira;
    private Integer cvv;
}
