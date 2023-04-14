package com.pagamento.pubsub;

import com.pagamento.model.Pagamento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
public class PullPagamentosComponent {

    private final Sinks.Many<PubSubMessage> sink;

    public Mono<Pagamento> pullNewPagamento(final Pagamento pagamento) {
        return Mono.fromCallable(() -> {
            log.info("Iniciando pull de pagamento - {}", pagamento);
            String id = pagamento.getId();
            return new PubSubMessage(id, pagamento);
        })
        .subscribeOn(Schedulers.parallel())
        .doOnNext(pubSubMessage -> this.sink.tryEmitNext(pubSubMessage))
        .doOnNext(evento -> log.info("Evento de pagamento criado - {}", evento))
        .thenReturn(pagamento);
    }

}
