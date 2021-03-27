package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum StatusEnum {
    APROVADA("aprovada"),
    REPROVADA("reprovada"),
    TRANSPORTE("transporte"),
    TROCA("troca"),
    ENTREGUE("entregue");

    private String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }
}
