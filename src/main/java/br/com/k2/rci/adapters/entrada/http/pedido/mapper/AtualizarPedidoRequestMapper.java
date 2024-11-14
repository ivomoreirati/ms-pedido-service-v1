package br.com.k2.rci.adapters.entrada.http.pedido.mapper;

import br.com.k2.rci.adapters.entrada.http.pedido.AtualizarPedidoRequest;
import br.com.k2.rci.application.core.domain.AtualizarPedido;
import org.springframework.stereotype.Component;

@Component
public class AtualizarPedidoRequestMapper {

    public AtualizarPedido toAtualizarPedido(AtualizarPedidoRequest request) {

        return new AtualizarPedido(request.status(), request.produtos());
    }
}
