package br.com.k2.rci.application.ports.saida;

import br.com.k2.rci.application.core.domain.BuscarPedido;
import br.com.k2.rci.application.core.domain.Pedido;

import java.util.Optional;

public interface PedidoPort {

    Pedido salvarPedido(Pedido pedido);

    Optional<BuscarPedido> buscarPedidoPorId(Long id);

    Pedido atualizarPedido(Pedido pedido);
}
