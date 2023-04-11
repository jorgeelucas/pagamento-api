package com.pagamento.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pagamento.model.Pagamento;
import com.pagamento.service.PagamentoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService service;

    @PostMapping
    public Mono<Pagamento> novoPagamento(@RequestBody NovoPagamentoRequest request){
        return Mono.empty();
    }

    @GetMapping
    public Mono<Pagamento> get(@RequestParam("id") String id) {
        return Mono.empty();
    }


    @Data
    public static class NovoPagamentoRequest {
        @JsonProperty("usuario_id")
        private String usuarioId;
    }
}
