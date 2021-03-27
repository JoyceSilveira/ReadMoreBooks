package br.com.fatec.readmorebookstore.dominio;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Livro extends AbstractEntidade {
    private boolean ativo = true;
    private String titulo;
    private String editora;
    private String autor;
    private String ano;
    private String edicao;
    private String isbn;
    private String numPaginas;
    private String sinopse;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "livro")
//    private List<CategoriaLivro> categoriasVinculadas = new ArrayList<>();
    private String categoria;
//    private GrupoPrecificacao grupoPrecificacao;
    private String grupoPrecificacao;
    private String preco;
    private String codBarra;
    private String altura;
    private String largura;
    private String peso;
    private String profundidade;
    private String estoque;

//    @Transient
//    private List<CategoriaLivro> categorias = new ArrayList<>();
}
