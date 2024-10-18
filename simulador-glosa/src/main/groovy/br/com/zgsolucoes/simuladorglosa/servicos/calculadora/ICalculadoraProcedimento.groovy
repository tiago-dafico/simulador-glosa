package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraProcedimento extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.55

	final BigDecimal inflator = INFLATOR

	@Override
	BigDecimal calculaValorTabela(TabelaDePrecos itemTabela) {

		BigDecimal desconto = 0

		if (itemTabela.codigo.toString().startsWith('4')) {
			desconto = 20
		}

		return itemTabela.valor * INFLATOR - desconto
	}

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.PROCEDIMENTO
	}
}
