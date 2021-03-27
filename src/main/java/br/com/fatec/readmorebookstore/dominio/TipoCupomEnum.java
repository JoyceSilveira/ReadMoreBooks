package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum TipoCupomEnum {
    PROMOCIONAL("Promocional"),
    TROCA("Troca");

    private String descricao;

    TipoCupomEnum(String descricao) {
        this.descricao = descricao;
    }
}
