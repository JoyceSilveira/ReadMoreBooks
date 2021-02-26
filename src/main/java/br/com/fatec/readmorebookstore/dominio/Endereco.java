package br.com.fatec.readmorebookstore.dominio;

//Tipo de residência (Casa, Apartamento etc.),Tipo Logradouro, Logradouro, Número,
//Bairro, CEP, Cidade, Estado e País.
//Todos os campos anteriores são de preenchimento obrigatório.
//Opcionalmente pode ser preenchido um campo observações.

public class Endereco extends AbstractEntidade {
    private String tipoResidencia;
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String bairro;

}
