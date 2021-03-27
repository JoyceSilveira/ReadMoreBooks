package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum TipoDocumentoEnum {
    CPF("CPF"),
    RG("RG");

    private String descricao;

    TipoDocumentoEnum(String descricao) {
        this.descricao = descricao;
    }
}

