package br.com.k2.rci.adapters.saida.http.produto.mapper;

import br.com.k2.rci.adapters.saida.http.produto.response.ProdutoResponse;
import br.com.k2.rci.application.core.domain.Produto;
import br.com.tlf.pco3.adapters.saida.http.parametro.response.TipoPagamentoResponse;
import br.com.tlf.pco3.application.core.domain.TipoPagamento;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toProduto(ProdutoResponse produtoResponse) {
        return new Produto(
                produtoResponse.codigo(),
                produtoResponse.descricao(),
                produtoResponse.ativo()
        );
    }
}