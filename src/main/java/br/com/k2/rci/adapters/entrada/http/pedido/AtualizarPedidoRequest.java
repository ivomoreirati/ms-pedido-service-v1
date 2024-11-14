package br.com.k2.rci.adapters.entrada.http.pedido;

import br.com.k2.rci.application.core.domain.Produto;

import java.util.List;

public record AtualizarPedidoRequest(String status, List<Produto> produtos) {

}
