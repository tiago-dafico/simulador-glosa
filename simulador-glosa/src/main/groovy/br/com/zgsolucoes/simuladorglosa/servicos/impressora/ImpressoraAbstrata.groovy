package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado

abstract class ImpressoraAbstrata {

    abstract Boolean podeImprimir(Boolean valor)

    abstract void montaImpressao(ItemCriticado itemCriticado, String texto) //@Todo Ao inves de usar String, usar Stringbuilder

}
