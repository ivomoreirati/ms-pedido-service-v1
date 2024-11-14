package br.com.k2.rci.application.core.domain;

public record Produto(Long id,
                      Integer codigo,
                      String descricao,
                      Boolean ativo) {
}
