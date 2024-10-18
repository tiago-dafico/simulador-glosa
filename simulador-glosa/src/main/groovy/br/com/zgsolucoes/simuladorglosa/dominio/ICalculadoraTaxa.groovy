package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraTaxa extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.15

	@Override
	BigDecimal calculaTipo(ItemFaturado valor) {
		return valor * INFLATOR
	}

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.TAXA
	}
}
