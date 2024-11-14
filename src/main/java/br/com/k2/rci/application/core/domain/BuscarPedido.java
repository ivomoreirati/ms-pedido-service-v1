package br.com.k2.rci.application.core.domain;

import java.util.List;

public record BuscarPedido(Long id,
                           String status,
                           List<Produto> produtos) {}
