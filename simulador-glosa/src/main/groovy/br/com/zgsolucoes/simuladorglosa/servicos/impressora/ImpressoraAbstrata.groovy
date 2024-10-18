package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado

abstract class ImpressoraAbstrata {

    protected static final String HEADER = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'

    abstract Boolean podeImprimir(Boolean valor)

    abstract String montaImpressaoTodos(final List<ItemCriticado> listaItems)

}
