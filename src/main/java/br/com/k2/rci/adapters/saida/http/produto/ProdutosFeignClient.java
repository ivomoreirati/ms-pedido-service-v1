package br.com.k2.rci.adapters.saida.http.produto;

import br.com.k2.rci.adapters.saida.http.produto.response.ProdutoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "ms-produto-service",
        path = "/",
        url = "${ms-produto-service.url}"
)
public interface ProdutosFeignClient {

    @GetMapping("produtos/{codigo}")
    ResponseEntity<ProdutoResponse> buscarProdutoPorCodigo(
            @PathVariable Long codigo
    );
}