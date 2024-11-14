package br.com.k2.rci.config;

import br.com.k2.rci.application.core.service.*;
import br.com.k2.rci.application.core.service.strategy.detalhepedido.*;
import br.com.k2.rci.application.core.service.strategy.etapasworkflow.*;
import br.com.k2.rci.application.ports.entrada.*;
import br.com.k2.rci.application.ports.saida.*;
import br.com.tlf.pco3.application.core.service.*;
import br.com.k2.rci.application.core.service.strategy.ValidadorAtualizarPagamentoStrategy;
import br.com.k2.rci.application.core.service.strategy.ValidadorAtualizarPagamentoStrategyFactory;
import br.com.k2.rci.application.core.service.strategy.ValidadorCriarPagamentoStrategy;
import br.com.k2.rci.application.core.service.strategy.classificacao.ValidadorDetalheEspecificacaoPagamento;
import br.com.k2.rci.application.core.service.strategy.classificacao.ValidadorEspecificacaoPagamento;
import br.com.k2.rci.application.core.service.strategy.classificacao.ValidadorFormaPagamento;
import br.com.k2.rci.application.core.service.strategy.classificacao.ValidadorTipoPagamento;
import br.com.k2.rci.application.core.service.strategy.configuracaopedidop3p1.ValidadorConfiguracaoPagamentoP3P1;
import br.com.tlf.pco3.application.core.service.strategy.detalhepedido.*;
import br.com.k2.rci.application.core.service.strategy.documentosfinanceiros.ValidadorDocumentosFinanceiros;
import br.com.tlf.pco3.application.core.service.strategy.etapasworkflow.*;
import br.com.k2.rci.application.core.service.strategy.eventojuridico.ValidadorEventoJuridico;
import br.com.k2.rci.application.core.validator.CpfCnpjValidator;
import br.com.tlf.pco3.application.ports.entrada.*;
import br.com.tlf.pco3.application.ports.saida.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class BeansConfig {

    @Bean
    PedidoServicePort pedidoService(PedidoPort pedidoPort,
                                       DadosComplementaresPagamentoService dadosComplementaresPagamentoService,
                                       List<ValidadorCriarPagamentoStrategy> validadorCriarPagamentoStrategies,
                                       List<ValidadorSecaoFormaPagamentoStrategy> validadorSecaoFormaPagamentoStrategies,
                                       ValidadorAtualizarPagamentoStrategyFactory validadorAtualizarPagamentoStrategyFactory,
                                       UsuarioPagamentoPort usuarioPagamentoPort) {

        return new PedidoService(
            pedidoPort,
            dadosComplementaresPagamentoService,
            validadorCriarPagamentoStrategies,
            validadorSecaoFormaPagamentoStrategies,
            validadorAtualizarPagamentoStrategyFactory,
            usuarioPagamentoPort);
    }

    @Bean
    FormaPagamentoServicePort buscarFormaPagamentoService(FormaPagamentoPort formaPagamentoPort) {
        return new FormaPagamentoService(formaPagamentoPort);
    }

    @Bean
    TipoVinculoJuridicoServicePort tipoVinculoJuridicoService(TipoVinculoJuridicoPort tipoVinculoJuridicoPort) {
        return new TipoVinculoJuridicoService(tipoVinculoJuridicoPort);
    }

    @Bean
    TipoDocumentoServicePort tipoDocumentoService(TipoDocumentoPort tipoDocumentoPort,
                                                  ChecklistPort checklistPort) {
        return new TipoDocumentoService(tipoDocumentoPort, checklistPort);
    }

    @Bean
    TipoPrazoServicePort tipoPrazoService(TipoPrazoPort tipoPrazoPort) {
        return new TipoPrazoService(tipoPrazoPort);
    }

    @Bean
    CalcularPrevisaoPagamentoServicePort calcularPrazoPagamentoService(ConsultarDiasAdicionaisHttpPort consultarDiasAdicionaisHttpPort,
                                                                       CalcularProximoDiaUtilHttpPort calcularProximoDiaUtilHttpPort,
                                                                       JanelaPagamentoPort janelaPagamentoPort) {
        return new CalcularPrevisaoPagamentoService(
            consultarDiasAdicionaisHttpPort,
            calcularProximoDiaUtilHttpPort,
            janelaPagamentoPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorTipoPagamento(ProdutosPort produtosPort) {
        return new ValidadorTipoPagamento(produtosPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorEspecificacaoPagamento(ProdutosPort produtosPort) {
        return new ValidadorEspecificacaoPagamento(produtosPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorDetalheEspecificacaoPagamento(ProdutosPort produtosPort) {
        return new ValidadorDetalheEspecificacaoPagamento(produtosPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorFormaPagamento() {
        return new ValidadorFormaPagamento();
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorContabilidade(ProdutosPort produtosPort) {
        return new ValidadorContabilidade(produtosPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorPrazos(CalcularPrevisaoPagamentoServicePort calcularPrevisaoPagamentoServicePort) {

        return new ValidadorPrazos(calcularPrevisaoPagamentoServicePort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorValorMulta(ProdutosPort produtosPort) {
        return new ValidadorValorMulta(produtosPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorValorPagamento() {
        return new ValidadorValorPagamento();
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorValorTotal() {
        return new ValidadorValorTotal();
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorDocumentosFinanceiros(TipoDocumentoServicePort tipoDocumentoServicePort,
                                                                   ArquivoPort arquivoPort) {

        return new ValidadorDocumentosFinanceiros(tipoDocumentoServicePort, arquivoPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorEventoJuridico(ProcessoPort processoPort,
                                                            TipoVinculoJuridicoPort tipoVinculoJuridicoPort) {

        return new ValidadorEventoJuridico(processoPort, tipoVinculoJuridicoPort);
    }

    @Bean
    CentroCustoContaContabilPort centroCustoContaContabilPort(CentroCustoContaContabilPort centroCustoContaContabilPort) {
        return centroCustoContaContabilPort;
    }

    @Bean
    CpfCnpjValidator cpfCnpjValidator() {
        return new CpfCnpjValidator();
    }

    @Bean
    ValidadorSecaoFormaPagamentoStrategy validadorFormaPagamentoBoleto(CpfCnpjValidator cpfCnpjValidator) {
        return new ValidadorFormaPagamentoBoleto(cpfCnpjValidator);
    }

    @Bean
    ValidadorSecaoFormaPagamentoStrategy validadorFormaPagamentoGuia(CpfCnpjValidator cpfCnpjValidator,
                                                                     ProdutosPort produtosPort) {

        return new ValidadorFormaPagamentoGuia(cpfCnpjValidator, produtosPort);
    }

    @Bean
    PagamentoCriadoServicePort pedidoCriadoService(NotificarPagamentoCriadoPort notificarPagamentoCriadoPort,
                                                      PagamentoOutboxPort pedidoOutboxPort) {

        return new PagamentoCriadoService(notificarPagamentoCriadoPort, pedidoOutboxPort);
    }

    @Bean
    ValidadorSecaoFormaPagamentoStrategy validadorFormaPagamentoDepositoConta(CpfCnpjValidator cpfCnpjValidator,
                                                                              ProdutosPort produtosPort) {

        return new ValidadorFormaPagamentoDepositoConta(cpfCnpjValidator, produtosPort);
    }

    @Bean
    DadosComplementaresPagamentoService dadosComplementaresPagamentoService(ProdutosPort produtosPort,
                                                                            FormaPagamentoPort formaPagamentoPort,
                                                                            StatusPagamentoPort statusPagamentoPort,
                                                                            TipoPrazoPort tipoPrazoPort,
                                                                            CentroCustoContaContabilPort centroCustoContaContabilPort,
                                                                            TipoVinculoJuridicoPort tipoVinculoJuridicoPort,
                                                                            TipoDocumentoPort tipoDocumentoPort) {
        return new DadosComplementaresPagamentoService(
            produtosPort,
            formaPagamentoPort,
            statusPagamentoPort,
            tipoPrazoPort,
            centroCustoContaContabilPort,
            tipoVinculoJuridicoPort,
            tipoDocumentoPort);
    }

    @Bean
    ValidadorCriarPagamentoStrategy validadorConfiguracaoPagamentoP3P1(ConfiguracaoPagamentoP3Port configuracaoPagamentoP3Port) {
        return new ValidadorConfiguracaoPagamentoP3P1(configuracaoPagamentoP3Port);
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorAprovada() {
        return new ValidadorAprovada();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorCriticaIdentificada() {
        return new ValidadorCriticaIdentificada();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorIdSapInformado() {
        return new ValidadorIdSapInformado();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorEstornada() {
        return new ValidadorCancelada();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorCompensada() {
        return new ValidadorCompensada();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorReenviada() {
        return new ValidadorReenviada();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorRejeitadaAdvogado() {
        return new ValidadorRejeitadaAdvogado();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorRejeitadaGerente() {
        return new ValidadorRejeitadaGerente();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorRejeitadaDiretor() {
        return new ValidadorRejeitadaDiretor();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategy validadorComprovanteInformado() {
        return new ValidadorComprovanteInformado();
    }

    @Bean
    ValidadorAtualizarPagamentoStrategyFactory validadorAtualizarPagamentoStrategyFactory(
        List<ValidadorAtualizarPagamentoStrategy> validadorAtualizarPagamentoStrategies
    ) {

        return new ValidadorAtualizarPagamentoStrategyFactory(validadorAtualizarPagamentoStrategies);
    }

    @Bean
    ConfiguracoesPagamentoP3ServicePort configuracoesPagamentoP3Service(ConfiguracaoPagamentoP3Port configuracaoPagamentoP3Port) {
        return new ConfiguracoesPagamentoP3Service(configuracaoPagamentoP3Port);
    }
}
