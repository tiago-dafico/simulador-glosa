package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import java.text.DecimalFormat
import java.text.NumberFormat

class ImpressoraComFormatacao extends ImpressoraAbstrata {
	final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)

	@Override
	boolean podeImprimir() {
		return false
	}

	@Override
	String montaImpressao() {
		String texto
		texto += itemCriticado.codigo
		texto += ';'
		texto += CURRENCY_FORMAT.format(valorFaturado)
		texto += ';'
		texto += CURRENCY_FORMAT.format(valorCalculado)
		texto += ';'
		texto += CURRENCY_FORMAT.format(valorCriticado)
		texto += '\n'
		return texto
	}
}
