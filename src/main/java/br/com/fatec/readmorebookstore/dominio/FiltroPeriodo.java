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
public class FiltroPeriodo extends AbstractEntidade{
    private Integer mesInicio;
    private Integer anoInicio;
    private Integer mesFim;
    private Integer anoFim;
}
