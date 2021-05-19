package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum StatusEnum {
    PROCESSAMENTO("processamento"),
    APROVADA("aprovada"),
    REPROVADA("reprovada"),
    TRANSPORTE("transporte"),
    TROCA("troca"),
    TROCADO("trocado"),
    RECUSADO("recusado"),
    ENTREGUE("entregue");

    private String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }
}
