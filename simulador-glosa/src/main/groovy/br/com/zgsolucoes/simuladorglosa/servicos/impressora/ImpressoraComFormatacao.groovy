package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado

import java.text.DecimalFormat
import java.text.NumberFormat

class ImpressoraComFormatacao extends ImpressoraAbstrata {
	final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)

	@Override
	Boolean podeImprimir(Boolean valor) {
		return valor == true
	}

	@Override
	void montaImpressao(ItemCriticado itemCriticado, String cabecalho) {
		cabecalho += itemCriticado.codigo
		cabecalho += ';'
		cabecalho += CURRENCY_FORMAT.format(valorFaturado)
		cabecalho += ';'
		cabecalho += CURRENCY_FORMAT.format(valorCalculado)
		cabecalho += ';'
		cabecalho += CURRENCY_FORMAT.format(valorCriticado)
		cabecalho += '\n'
	}
}
