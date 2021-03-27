package br.com.fatec.readmorebookstore.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Documento extends AbstractEntidade{
    private String codigo;
    private String validade;
    private TipoDocumentoEnum tipoDocumento;
}
