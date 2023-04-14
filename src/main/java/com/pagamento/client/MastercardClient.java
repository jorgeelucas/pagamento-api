package com.pagamento.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagamento.client.dto.PagamentoCartaoRequest;
import com.pagamento.client.dto.PagamentoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MastercardClient {

    private static final String PAYMENT_URI = "/credit-card-payments";

    private final WebClient client;
    private final ObjectMapper mapper;

    public MastercardClient(WebClient.Builder clientBuilder, ObjectMapper mapper) {
        this.client = clientBuilder
                .baseUrl("http://localhost:8082")
                .build();
        this.mapper = mapper;
    }

    public Mono<PagamentoResponse> processarPagamento(PagamentoCartaoRequest request) {
        String payload = "";

        try {
            payload = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return this.client
                .post()
                .uri(PAYMENT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchangeToMono(result -> {
                    if (result.statusCode().is2xxSuccessful()) {
                        return result.bodyToMono(PagamentoResponse.class);
                    } else {
                        return Mono.error(new RuntimeException("Erro na chamada"));
                    }
                });

    }

}
