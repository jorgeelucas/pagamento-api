package com.pagamento.repository;

import com.pagamento.model.Pagamento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PagamentoRepository extends ReactiveCrudRepository<Pagamento, String> {
}
