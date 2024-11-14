package br.com.k2.rci.adapters.saida.repository.pedido;

import br.com.k2.rci.application.core.domain.BuscarPedido;
import br.com.k2.rci.application.core.domain.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PedidoEntityMapper {

    public PedidoEntity toPedidoEntity(Pedido pedido) {

        var pedidoEntity = PedidoEntity.builder()
            .id(pedido.id())
            .status(pedido.status())
            .build();

        return pedidoEntity;
    }

    public Pedido toPedido(PedidoEntity pedidoEntity) {

        return new Pedido(
            pedidoEntity.getId(),
            pedidoEntity.getStatus(),
            pedidoEntity.getProdutos()
        );
    }

    public BuscarPedido toBuscarPedido(PedidoEntity pedidoEntity) {


        return new BuscarPedido(
            pedidoEntity.getId(),
            pedidoEntity.getStatus(),
            pedidoEntity.getProdutos()
        );
    }
}
