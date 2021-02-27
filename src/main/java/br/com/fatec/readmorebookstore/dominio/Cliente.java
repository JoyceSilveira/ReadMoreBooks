package br.com.fatec.readmorebookstore.dominio;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cliente extends AbstractEntidade {
    private String genero;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    // Deve ser composto pelo tipo, DDD e número
    private String telefone;
    private String email;
    private String senha;
    @Transient
    private List<Endereco> enderecos;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
    private List<EnderecoCliente> enderecosVinculados = new ArrayList<>();
}
