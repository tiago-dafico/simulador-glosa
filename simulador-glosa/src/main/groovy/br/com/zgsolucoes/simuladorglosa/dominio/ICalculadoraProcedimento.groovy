package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraProcedimento extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.55

	@Override
	BigDecimal calculaTipo(TabelaDePrecos itemTabela) {

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

	@Override
	BigDecimal getInflator() {
		return INFLATOR
	}
}
