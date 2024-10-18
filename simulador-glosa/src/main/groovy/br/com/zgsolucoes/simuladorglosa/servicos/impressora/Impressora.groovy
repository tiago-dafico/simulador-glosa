package br.com.zgsolucoes.simuladorglosa.servicos.impressora

abstract class Impressora {

    boolean podeImprimir() {
        return true
    }

    abstract String montaImpressao()

}
