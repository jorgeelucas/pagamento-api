package com.pagamento.client.dto;

public record CardRequest(String number, String secret, String validUntil) {
}
