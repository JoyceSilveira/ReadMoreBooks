package br.com.fatec.readmorebookstore.dominio;

//Para todo cliente cadastrado é obrigatório o cadastro dos seguintes dados:
//Gênero, Nome, Data de Nascimento, CPF, Telefone (deve ser composto pelo tipo, DDD e número),
//e-mail, senha, endereço residencial.

import java.time.LocalDate;

public class Cliente extends AbstractEntidade {
    private String genero;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    // Deve ser composto pelo tipo, DDD e número
    private String telefone;
    private String email;
    private String senha;
    private List<EnderecoCliente> enderecos;
}
