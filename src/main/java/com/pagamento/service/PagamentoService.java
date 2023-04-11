package com.pagamento.service;

import com.pagamento.model.Pagamento;
import com.pagamento.repository.InMemoryPagamentoDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.pagamento.controller.PagamentoController.NovoPagamentoRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final InMemoryPagamentoDB repository;

    public Mono<Pagamento> save(NovoPagamentoRequest request) {
        log.info("Iniciando pagamento - {}", request);
        // implementação
        return Mono.empty();
    }


    public Mono<Pagamento> get(String id) {
        log.info("Iniciando a busca de um pagamento pelo id - {}", id);
        return Mono.empty();
    }
}
