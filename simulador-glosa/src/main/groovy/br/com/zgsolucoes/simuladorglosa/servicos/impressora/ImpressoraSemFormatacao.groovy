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
class ImpressoraSemFormatacao extends ImpressoraAbstrata {
	private static final String HEADER = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'

	@Override
	Boolean podeImprimir(Boolean valor) {
		return valor == false
	}

	@Override
	String montaImpressao(ItemCriticado itemCriticado, String cabecalho) {
		"${cabecalho}${itemCriticado.codigo};${itemCriticado.valorFaturado};${itemCriticado.valorCalculado.setScale(2)};${itemCriticado.valorCriticado.setScale(2)}\n"
	}

	@Override
	String montaImpressaoTodos(final List<ItemCriticado> listaItems) {
		final String listaItemsFormatados = listaItems.collect{itemCriticado ->
			"${itemCriticado.codigo};" +
					"${itemCriticado.valorFaturado};" +
					"${itemCriticado.valorCalculado};" +
					"${itemCriticado.valorCriticado}"
		}.join('\n')

		return HEADER + listaItemsFormatados
	}
}
