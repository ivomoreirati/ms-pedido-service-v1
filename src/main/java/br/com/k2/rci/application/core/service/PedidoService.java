package br.com.k2.rci.application.core.service;

import br.com.k2.rci.application.core.domain.AtualizarPedido;
import br.com.k2.rci.application.core.domain.BuscarPedido;
import br.com.k2.rci.application.core.domain.Pedido;
import br.com.k2.rci.application.core.domain.SalvarPedido;
import br.com.k2.rci.application.core.erros.BusinessException;
import br.com.k2.rci.application.ports.entrada.PedidoServicePort;
import br.com.k2.rci.application.ports.saida.PedidoPort;

public class PedidoService implements PedidoServicePort {
    
    private final PedidoPort pedidoPort;
    private static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado";

    public PedidoService(PedidoPort pedidoPort) {

        this.pedidoPort = pedidoPort;
    }

    @Override
    public Pedido criarPedido(SalvarPedido salvarPedido) {

        var pedido = montarPedidoParaCriar(salvarPedido);

        return pedidoPort.salvarPedido(pedido);
    }

    @Override
    public BuscarPedido buscarPedidoPorId(Long id) {
        return pedidoPort.buscarPedidoPorId(id)
            .orElseThrow(() -> new BusinessException(PEDIDO_NAO_ENCONTRADO));
    }

    @Override
    public Pedido atualizarPedido(Long id, AtualizarPedido atualizarPedido) {
        var buscarPagamento = buscarPedidoPorId(id);
        var pedido = montarPedidoParaAtualizar(atualizarPedido, buscarPagamento);

        return pedidoPort.atualizarPedido(pedido);
    }

    private Pedido montarPedidoParaCriar(SalvarPedido salvarPedido) {


        return new Pedido(
            salvarPedido.status(),
            salvarPedido.produtos());
    }

    private Pedido montarPedidoParaAtualizar(AtualizarPedido atualizarPedido,
                                             BuscarPedido buscarPedido) {

        return new Pedido(
            buscarPedido.id(),
            atualizarPedido.status(),
            atualizarPedido.produtos()
        );
    }
}
