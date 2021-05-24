package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LogDesativacaoLivro extends AbstractEntidade{
    @ManyToOne(cascade= CascadeType.PERSIST)
    private Livro livro;
    private String justificativa;
    @Enumerated(EnumType.STRING)
    private CategoriaInativacaoEnum categoriaInativacao;
    @Enumerated(EnumType.STRING)
    private CategoriaAtivacaoEnum categoriaAtivacao;
}
