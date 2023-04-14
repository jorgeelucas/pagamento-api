package com.pagamento.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@With
@Document(value = "pagamentos")
public class Pagamento {

    private String id;
    private String usuarioId;
    private Status status;
    private Cartao cartao;
    private BigDecimal valor;
    private Instant dataCriacao;
    private Instant dataModificacao;

    public enum Status {
        PENDENTE, CONCLUIDO
    }
}
