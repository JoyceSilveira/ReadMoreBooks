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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "livro")
    private List<CategoriaLivro> categoriasVinculadas = new ArrayList<>();
//    private GrupoPrecificacao grupoPrecificacao;
//    private String categoria;
    private String grupoPrecificacao;
    private Double custo;
    private Double preco;
    private String codBarra;
    private String altura;
    private String largura;
    private String peso;
    private String profundidade;
    private Integer estoque;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "livro")
    private List<CompraLivro> compras;

    @Transient
    private List<Integer> categorias = new ArrayList<>();
}
