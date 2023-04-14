package com.pagamento.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "cartoes")
public class Cartao {

    private String numero;
    private String codSeguranca;
    private String dataExpiracao;

}
