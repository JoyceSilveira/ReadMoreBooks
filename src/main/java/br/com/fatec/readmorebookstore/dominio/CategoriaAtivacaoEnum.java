package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum CategoriaAtivacaoEnum {

    VOLTA_MERCADO("Volta mercado"),
    OUTROS("Outros");

    private String descricao;

    CategoriaAtivacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
