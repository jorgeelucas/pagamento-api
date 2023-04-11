package com.pagamento.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Pagamento {

    private String id;
    private String usuarioId;
    private Status status;
    private Instant dataCriacao;
    private Instant dataModificacao;

    public enum Status {
        PENDENTE, CONCLUIDO
    }
}
