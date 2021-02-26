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
public class Estado extends AbstractEntidade {

	private String sigla;
	private String nome;
	private String pais = "Brasil";

	public Estado(String sigla) {
		this.sigla = sigla;
	}
}
