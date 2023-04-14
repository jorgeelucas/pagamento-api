package com.pagamento.client.dto;

import java.math.BigDecimal;

public record PagamentoCartaoRequest(CardRequest card, BigDecimal amount) {
}
