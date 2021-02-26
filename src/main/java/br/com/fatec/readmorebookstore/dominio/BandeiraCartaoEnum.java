package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;

@Getter
public enum BandeiraCartaoEnum {

    MASTERCARD("Mastercard"),
    VISA("Visa"),
    AMERICAN_EXPRESS("American express"),
    HIPERCARD("Hipercard"),
    ELO("Elo");

    private String descricao;

    BandeiraCartaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
