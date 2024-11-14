package br.com.k2.rci.adapters.entrada.http.pedido;

import br.com.k2.rci.adapters.entrada.http.pedido.mapper.AtualizarPedidoRequestMapper;
import br.com.k2.rci.application.core.domain.BuscarPedido;
import br.com.k2.rci.application.core.domain.SalvarPedido;
import br.com.k2.rci.application.ports.entrada.PedidoServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Validated
class PedidoController {

    private final PedidoServicePort port;
    private final AtualizarPedidoRequestMapper atualizarPedidoRequestMapper;

    @Operation(summary = "Criar pedido", description = "Cadastrar um novo pedido",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            })
    @PostMapping
    public ResponseEntity<Void> criarPedido(@Valid @RequestBody SalvarPedido salvarPedido) {

        var pedido = port.criarPedido(salvarPedido);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pedido.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @Operation(summary = "Buscar pedido por id", description = "Busca um pedido na base de dados por id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
        })
    @GetMapping("/{id}")
    public ResponseEntity<BuscarPedido> buscarPedidoPorId(@PathVariable Long id) {

        return ResponseEntity.ok(port.buscarPedidoPorId(id));
    }

    @Operation(summary = "Atualizar pedido", description = "Atualiza um pedido na base de dados",
        responses = {
            @ApiResponse(responseCode = "204", description = "Pedido atualizado com sucesso"),
        })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPedido(@PathVariable Long id,
                                                @RequestBody AtualizarPedidoRequest request) {

        port.atualizarPedido(id, atualizarPedidoRequestMapper.toAtualizarPedido(request));

        return ResponseEntity.noContent().build();
    }
}
