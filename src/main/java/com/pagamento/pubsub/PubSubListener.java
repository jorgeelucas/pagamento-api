package com.pagamento.pubsub;

import com.pagamento.client.MastercardClient;
import com.pagamento.client.dto.CardRequest;
import com.pagamento.client.dto.PagamentoCartaoRequest;
import com.pagamento.model.Pagamento;
import com.pagamento.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class PubSubListener implements InitializingBean {

    private final Sinks.Many<PubSubMessage> sink;
    private final PagamentoRepository pagamentoRepository;
    private final MastercardClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        sink.asFlux()
                .delayElements(Duration.ofSeconds(10))
                .subscribe(
                        next -> {
                            log.info("Inicializando listener onNext - {}", next);
                            final var id = next.id();
                            pagamentoRepository.findById(id)
                                    .flatMap(pagamento -> {
                                        log.info("Enviando dados para mastercard-api - {}", pagamento);
                                        var cardRequest = new CardRequest(pagamento.getCartao().getNumero(), pagamento.getCartao().getCodSeguranca(), pagamento.getCartao().getDataExpiracao());
                                        var request = new PagamentoCartaoRequest(cardRequest, pagamento.getValor());
                                        return client.processarPagamento(request);
                                    })
                                    .flatMap(pagamento -> {
                                        log.info("Alterando o status do pagamento - {}", pagamento);
                                        var statusConcluido = Pagamento.Status.CONCLUIDO;

                                        return pagamentoRepository.save(next.pagamento()
                                                .withStatus(statusConcluido)
                                                .withDataModificacao(Instant.now()));
                                    })
                                    .subscribe();
                        },
                        err -> {
                            log.error("Error: {}", err.getMessage());
                        },
                        () -> {
                            log.info("Completed");
                        }
                );
    }
}
