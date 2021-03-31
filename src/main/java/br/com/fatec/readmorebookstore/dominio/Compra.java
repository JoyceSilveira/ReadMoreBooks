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
public class Compra extends AbstractEntidade {
    @ManyToOne(cascade= CascadeType.PERSIST)
    private Cliente cliente;
    private String frete;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compra")
    private List<Cupom> cuponsVinculados = new ArrayList<>();
    private Double valorTotal;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private String previsaoEntrega;

    @Transient
    private List<Cupom> cupons = new ArrayList<>();
}
