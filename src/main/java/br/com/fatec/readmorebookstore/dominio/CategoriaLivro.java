package br.com.fatec.readmorebookstore.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoriaLivro extends AbstractEntidade{
    @ManyToOne
    private Livro livro;
    @ManyToOne
    private Categoria categoria;
}
