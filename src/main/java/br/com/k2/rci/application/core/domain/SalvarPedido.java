package br.com.k2.rci.application.core.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SalvarPedido(@NotBlank(message = STATUS_OBRIGATORIO) String status,
                           @NotEmpty(message = PRODUTO_OBRIGATORIO) List<Produto> produtos) {

    private static final String STATUS_OBRIGATORIO = "Status do pedido deve ser preenchido.";
    private static final String PRODUTO_OBRIGATORIO = "Pelo menos um produto deve ser informado.";

}
