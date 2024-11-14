package br.com.k2.rci.adapters.saida.http.produto;

import br.com.k2.rci.adapters.saida.http.produto.mapper.ProdutoMapper;
import br.com.k2.rci.application.core.domain.Produto;
import br.com.k2.rci.application.ports.saida.ProdutosPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutosHttpAdapter implements ProdutosPort {

    private final ProdutosFeignClient produtosFeignClient;

    private final ProdutoMapper produtoMapper;

    @Override
    public Optional<Produto> buscarProdutoPorCodigo(Long codigoProduto) {

        try {
            var response = produtosFeignClient.buscarProdutoPorCodigo(codigoProduto);

            return response.hasBody() ? Optional.ofNullable(produtoMapper.toProduto(response.getBody())) : Optional.empty();

        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }
}