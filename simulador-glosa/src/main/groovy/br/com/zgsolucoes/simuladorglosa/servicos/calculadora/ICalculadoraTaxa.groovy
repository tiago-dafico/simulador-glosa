package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraTaxa extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.15

	final BigDecimal inflator = INFLATOR

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.TAXA
	}
}
