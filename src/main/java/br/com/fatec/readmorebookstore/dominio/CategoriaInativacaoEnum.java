package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum CategoriaInativacaoEnum {

    FORA_MERCADO("Fora mercado"),
    OUTROS("Outros"),
    SEM_ESTOQUE("Sem estoque");

    private String descricao;

    CategoriaInativacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
