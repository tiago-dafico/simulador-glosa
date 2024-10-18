package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraTaxa extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.15


	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.TAXA
	}

	@Override
	BigDecimal getInflator() {
		return INFLATOR
	}
}
