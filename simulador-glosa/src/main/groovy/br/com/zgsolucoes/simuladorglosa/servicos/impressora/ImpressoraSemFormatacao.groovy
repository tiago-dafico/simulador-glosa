package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado

import java.text.DecimalFormat
import java.text.NumberFormat

class ImpressoraSemFormatacao extends ImpressoraAbstrata {
	final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)


	@Override
	Boolean podeImprimir(Boolean valor) {
		return valor
	}

	@Override
	String montaImpressao(ItemCriticado itemCriticado, String cabecalho) {
		cabecalho += itemCriticado.codigo
		cabecalho += ';'
		cabecalho += valorFaturado
		cabecalho += ';'
		cabecalho += valorCalculado.setScale(2)
		cabecalho += ';'
		cabecalho += valorCriticado.setScale(2)
		cabecalho += '\n'
		return cabecalho
	}
}
