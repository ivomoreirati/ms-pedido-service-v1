package br.com.k2.rci.adapters.saida.repository.pedido;

import org.springframework.data.repository.CrudRepository;

interface PedidoRepository extends CrudRepository<PedidoEntity, Long> {

//    @Query("SELECT p FROM PedidoEntity p " +
//        " join p.status sp " +
//        " WHERE p.status = :numeroProcesso " +
//        " ORDER BY sp.ordem asc, p.dataCriacao desc ")
//    List<PedidoEntity> buscarPorNumeroProcesso(String numeroProcesso);
//
//    @Query("SELECT p.codigoPagamento FROM PedidoEntity p" +
//            " WHERE YEAR(p.dataCriacao) = :ano" +
//            " ORDER BY p.dataCriacao desc")
//    List<String> buscarCodigosPagamentoPorAno(@Param("ano") int ano, Pageable pageable);

}
