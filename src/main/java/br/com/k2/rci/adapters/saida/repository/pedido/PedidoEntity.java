package br.com.k2.rci.adapters.saida.repository.pedido;

import br.com.k2.rci.application.core.domain.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @Column(name = "status", length = 50, nullable = false)
    private String status;

    @Column(name = "produtos", length = 2000, nullable = false)
    private List<Produto> produtos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PedidoEntity other))
            return false;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
