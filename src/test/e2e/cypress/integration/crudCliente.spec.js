/// <reference types="Cypress" />
context('Todo tests', () => {
    it('Exibir formulário cadastro cliente', () => {
        cy.viewport(1348, 879);
        cy.visit('/');
        cy.get('.redirecionamento').click();
    });

    it('Verificar campos obrigatórios', () => {
        cy.viewport(1348, 879);
        cy.get('#nome').type("Maria Feitosa Santos");
        cy.get('#genero').select("Feminino");
        cy.get('#dt_nascimento').type("1968-05-10");
        cy.get('#cpf').type("265798432");
        cy.get('#tel').type("11985632145");
        cy.get('#email').type("maria_f");
        cy.get('#senha').type("Mf@1111");
        cy.get('#rep-senha').type("Mf@1111");

        cy.get('#tp_residencia_end').select("Apartamento");
        cy.get('#tp_logradouro_end').select("Avenida");
        cy.get('#logradouro_end').type("Dr. Arnaldo");
        cy.get('#cep_end').type("08710787");
        cy.get('#numero_end').type("78");
        cy.get('#complemento_end').type("apto 6");
        cy.get('#bairro_end').type("Centro");
        cy.get('#cidade_end').type("Itaquera");
        cy.get(':nth-child(6) > :nth-child(2) > #identificacao').type("Casa");
        cy.get('#obs_end').type("Entregas com o porteiro");

        cy.get('#logradouro').type("Dr. Arnaldo");
        cy.get('#cep').type("0872654");
        cy.get('#numero').type("27");
        cy.get('#complemento');
        cy.get('#bairro').type("Vila Fátima");
        cy.get('#cidade').type("Itaquera");
        cy.get(':nth-child(12) > :nth-child(2) > #identificacao').type("Trabalho");
        cy.get('#obs');

        cy.get('#num_card').type("478565987412032");
        cy.get('#cod_seguranca').type("788");

        cy.get('.botao').click();

        cy.get('#nome_card').type("Maria F Santos");
        cy.get('.botao').click();
    });

    it('Verificar cpf válido', () => {
        cy.viewport(1348, 879);
        cy.get('#cpf').type("10");
        cy.get('.botao').click();
    });

    it('Verificar e-mail válido', () => {
        cy.viewport(1348, 879);
        cy.get('#email').type("@hotmail.com");
        cy.get('.botao').click();
    });

    it('Verificar senha com todos os requisitos', () => {
        cy.viewport(1348, 879);
        cy.get('#senha').type("1");
        cy.get('.botao').click();
    });

    it('Verificar senhas iguais', () => {
        cy.viewport(1348, 879);
        cy.get('#rep-senha').type("1");
        cy.get('.botao').click();
    });

    it('Verificar cep válido', () => {
        cy.viewport(1348, 879);
        cy.get('#cep').type("5");
        cy.get('.botao').click();
    });

    it('Verificar número de cartão válido', () => {
        cy.viewport(1348, 879);
        cy.get('#num_card').type("8");
        cy.get('.botao').click();
    });

    it('Editar dados pessoais', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get(':nth-child(1) > .botao-editar > .editar').click();
        cy.get('#tel').clear();
        cy.get('#tel').type("11985302477");
        cy.get('.botao').click();
    });

    it('Editar senha', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get('.alt-senha').click();
        cy.get('#senha').type("Mf@2222");
        cy.get('#rep-senha').type("Mf@2222");
        cy.get('.botao').click();
    });

    it('Editar endereço', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get(':nth-child(1) > :nth-child(8) > .editar').click();
        cy.get('#numero').clear();
        cy.get('#numero').type("1200");
        cy.get('.botao').click();
    });

    it('Adicionar endereço', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get(':nth-child(2) > .botao-editar > .editar').click();
        cy.get('#logradouro').type("Ipiranga");
        cy.get('#cep').type("08711596");
        cy.get('#numero').type("47");
        cy.get('#bairro').type("Centro");
        cy.get('#cidade').type("Arujá");
        cy.get('#identificacao').type("Casa mãe");
        cy.get('.botao').click();
    });

    it('Adicionar cartão', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get('[href="/clientes/add-cartao/1"]').click();
        cy.get('#bandeira').select("Visa");
        cy.get('#num_card').type("4123532648592154");
        cy.get('#cod_seguranca').type("235");
        cy.get('#nome_card').type("Maria F Santos");
        cy.get('.botao').click();
    });

    it('Editar cartão', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get(':nth-child(1) > :nth-child(5) > .editar').click();
        cy.get('#bandeira').select("Elo");
        cy.get('.botao').click();
    });

    it('Editar cartão preferencial', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/perfil-cliente/33');
        cy.get('.preferencial').click();
        cy.get(':nth-child(2) > [style="display: block;"] > .editar').click();
    });

    it('Exibir listagem de clientes', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/lista-cliente');
    });

    it('Inativar cliente', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/lista-cliente');
        cy.get(':nth-child(1) > .botao-acao > .excluir').click();
    });

    it('Ativar cliente', () => {
        cy.viewport(1348, 879);
        cy.visit('/clientes/lista-cliente');
        cy.get(':nth-child(1) > .botao-acao > .excluir').click();
    });
});