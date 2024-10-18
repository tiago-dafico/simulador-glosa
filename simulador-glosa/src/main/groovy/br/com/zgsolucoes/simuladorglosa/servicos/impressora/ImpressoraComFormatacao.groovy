package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado
import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Order
import jakarta.inject.Singleton

import java.text.DecimalFormat
import java.text.NumberFormat

@Order(1)
@CompileStatic
@Singleton
class ImpressoraComFormatacao extends ImpressoraAbstrata {
	private static final Locale LOCALE = new Locale('pt', 'BR')
	private static final String HEADER = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'

	final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)

	@Override
	Boolean podeImprimir(Boolean valor) {
		return valor == true
	}

	@Override
	String montaImpressao(ItemCriticado itemCriticado, String cabecalho) {
		"${cabecalho}${itemCriticado.codigo};${CURRENCY_FORMAT.format(itemCriticado.valorFaturado)};${CURRENCY_FORMAT.format(itemCriticado.valorCalculado)};${CURRENCY_FORMAT.format(itemCriticado.valorCriticado)}\n"
	}

	@Override
	String montaImpressaoTodos(final List<ItemCriticado> listaItems) {
		final String listaItemsFormatados = listaItems.collect{itemCriticado ->
			"${itemCriticado.codigo};" +
			"${CURRENCY_FORMAT.format(itemCriticado.valorFaturado)};" +
			"${CURRENCY_FORMAT.format(itemCriticado.valorCalculado)};" +
			"${CURRENCY_FORMAT.format(itemCriticado.valorCriticado)}"
		}.join('\n')

		return HEADER + listaItemsFormatados
	}
}
