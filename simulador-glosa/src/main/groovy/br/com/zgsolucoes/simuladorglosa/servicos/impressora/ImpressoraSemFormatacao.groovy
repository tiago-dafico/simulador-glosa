package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import java.text.DecimalFormat
import java.text.NumberFormat

class ImpressoraSemFormatacao extends ImpressoraAbstrata {
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
		texto += valorFaturado
		texto += ';'
		texto += valorCalculado.setScale(2)
		texto += ';'
		texto += valorCriticado.setScale(2)
		texto += '\n'
		return texto
	}
}
