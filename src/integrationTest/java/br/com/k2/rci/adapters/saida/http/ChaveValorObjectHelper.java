package br.com.k2.rci.adapters.saida.http;

public record ChaveValorObjectHelper(String chave, String valor) {

    public static ChaveValorObjectHelper criar(String chave, String valor) {
        return new ChaveValorObjectHelper(chave, valor);
    }
}
