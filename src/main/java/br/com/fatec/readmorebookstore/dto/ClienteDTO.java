package br.com.fatec.readmorebookstore.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClienteDTO extends AbstractDTO{
    private String genero;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String telefone;
    private String email;
    private String senha;

    private String tipoEndereco;
    private String tipoResidencia;
    private String tipoLogradouro;
    private String logradouro;
    private String numeroResidencia;
    private String bairro;
    private String cep;
    private String complemento;
    private String nomeCidade;
    private String siglaEstado;
    private String nomePais;
    private String observacoes;

    private String numeroCartao;
    private String nomeImpresso;
    private String bandeira;
    private String cvv;
}
