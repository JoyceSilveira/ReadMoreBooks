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
public class Endereco extends AbstractEntidade {
    @ManyToOne
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    private TipoEnderecoEnum tipoEndereco;
    private String tipoResidencia;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;
    @ManyToOne
    private Cidade cidade;
    // Preenchimento opcional
    private String observacoes;

    private String nomeCidade;
}
