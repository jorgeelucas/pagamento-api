package com.pagamento.service;

import com.pagamento.model.Pagamento;
import com.pagamento.repository.InMemoryPagamentoDB;
import com.pagamento.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.UUID;

import static com.pagamento.controller.PagamentoController.NovoPagamentoRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repository;

    public Mono<Pagamento> save(NovoPagamentoRequest request) {
        log.info("Iniciando pagamento - {}", request);

        final var novoId = UUID.randomUUID().toString();

        final var pagamento = Pagamento.builder()
                .id(novoId)
                .usuarioId(request.getUsuarioId())
                .cartao(request.getCartao())
                .valor(request.getValor())
                .status(Pagamento.Status.PENDENTE)
                .dataCriacao(Instant.now())
                .build();

        return Mono.defer(() -> {
            log.info("Persistindo pagamento no banco de dados - {}", novoId);
            return repository.save(pagamento);
        }).subscribeOn(Schedulers.boundedElastic());
    }


    public Mono<Pagamento> get(String id) {
        log.info("Iniciando a busca de um pagamento pelo id - {}", id);
        return repository.findById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(it -> log.info("Resgatando pagamento do banco de dados - {}", it))
                .switchIfEmpty(Mono.error(new RuntimeException("Pagamento não encontrado")));
    }
}
