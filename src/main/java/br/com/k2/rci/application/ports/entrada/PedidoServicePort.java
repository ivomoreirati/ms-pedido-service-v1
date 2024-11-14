package br.com.k2.rci.application.ports.entrada;

import br.com.k2.rci.application.core.domain.AtualizarPedido;
import br.com.k2.rci.application.core.domain.BuscarPedido;
import br.com.k2.rci.application.core.domain.Pedido;
import br.com.k2.rci.application.core.domain.SalvarPedido;

public interface PedidoServicePort {

    Pedido criarPedido(SalvarPedido salvarPedido);

    BuscarPedido buscarPedidoPorId(Long id);

    Pedido atualizarPedido(Long id, AtualizarPedido atualizarPedido);
}
