package br.com.k2.rci.adapters.saida.http.produto.response;

public record ProdutoResponse(Integer codigo,
                              String descricao,
                              Boolean ativo) {
}