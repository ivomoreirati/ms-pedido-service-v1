package br.com.k2.rci.application.ports.saida;

import br.com.k2.rci.application.core.domain.Produto;

import java.util.Optional;

public interface ProdutosPort {

    Optional<Produto> buscarProdutoPorCodigo(Long codigoProduto);
}