package br.com.k2.rci.adapters.saida.repository.pedido;

import br.com.k2.rci.adapters.saida.http.produto.ProdutosHttpAdapter;
import br.com.k2.rci.application.core.domain.BuscarPedido;
import br.com.k2.rci.application.core.domain.Pedido;
import br.com.k2.rci.application.ports.saida.PedidoPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PedidoAdapter implements PedidoPort {

    private final PedidoRepository pedidoRepository;
    private final PedidoEntityMapper pedidoEntityMapper;
    private final ProdutosHttpAdapter produtosHttpAdapter;

    @Override
    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        var pedidoEntity = pedidoEntityMapper.toPedidoEntity(pedido);

        var pedidoSalvo = pedidoRepository.save(pedidoEntity);

        return pedidoEntityMapper.toPedido(pedidoSalvo);
    }

    @Override
    public Optional<BuscarPedido> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).map(pedidoEntityMapper::toBuscarPedido);
    }

    @Override
    public Pedido atualizarPedido(Pedido pedido) {
        return salvarPedido(pedido);
    }
}
