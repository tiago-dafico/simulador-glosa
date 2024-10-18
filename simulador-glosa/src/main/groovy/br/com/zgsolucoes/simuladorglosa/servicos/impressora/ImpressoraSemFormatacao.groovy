package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado
import groovy.transform.CompileStatic
import jakarta.inject.Singleton

@CompileStatic
@Singleton
class ImpressoraSemFormatacao extends ImpressoraAbstrata {
	@Override
	Boolean podeImprimir(Boolean valor) {
		return !valor
	}

	@Override
	String montaImpressaoTodos(final List<ItemCriticado> listaItems) {
		final String listaItemsFormatados = listaItems.collect{itemCriticado ->
			"${itemCriticado.codigo};" +
					"${itemCriticado.valorFaturado};" +
					"${itemCriticado.valorCalculado.setScale(2)};" +
					"${itemCriticado.valorCriticado.setScale(2)}\n"
		}.join('')

		return HEADER + listaItemsFormatados
	}
}
