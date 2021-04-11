package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {
    @Transient
    private String msgTxt = "";
}
