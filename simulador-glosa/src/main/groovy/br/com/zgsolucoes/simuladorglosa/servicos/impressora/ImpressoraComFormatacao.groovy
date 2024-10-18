package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado
import groovy.transform.CompileStatic
import jakarta.inject.Singleton

import java.text.DecimalFormat
import java.text.NumberFormat

@CompileStatic
@Singleton
class ImpressoraComFormatacao extends ImpressoraAbstrata {

	private static final Locale LOCALE = new Locale('pt', 'BR')

	final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)

	@Override
	Boolean podeImprimir(Boolean valor) {
		return valor
	}

	@Override
	String montaImpressaoTodos(final List<ItemCriticado> listaItems) {
		final String listaItemsFormatados = listaItems.collect{itemCriticado ->
			"${itemCriticado.codigo};" +
			"${CURRENCY_FORMAT.format(itemCriticado.valorFaturado)};" +
			"${CURRENCY_FORMAT.format(itemCriticado.valorCalculado)};" +
			"${CURRENCY_FORMAT.format(itemCriticado.valorCriticado)}\n"
		}.join('')

		return HEADER + listaItemsFormatados
	}
}
