package br.com.k2.rci.application.core.domain;

import java.util.List;

public record Pedido(Long id,
                     String status,
                     List<Produto> produtos) {

    public Pedido(String status,List<Produto> produtos) {

        this(
                null,
                status,
                produtos);
    }
}