package com.pagamento.client.dto;

import java.math.BigDecimal;

public record PagamentoResponse(BigDecimal availableLimit) {
}
