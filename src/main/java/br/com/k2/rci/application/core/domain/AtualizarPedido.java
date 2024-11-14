package br.com.k2.rci.application.core.domain;

import java.util.List;

public record AtualizarPedido(String status, List<Produto> produtos) {}
