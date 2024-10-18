package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import groovy.transform.Undefined

class ICalculadoraMaterial extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.20

	@Override
	BigDecimal getInflator() {
		return INFLATOR
	}

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.MATERIAL
	}
}
