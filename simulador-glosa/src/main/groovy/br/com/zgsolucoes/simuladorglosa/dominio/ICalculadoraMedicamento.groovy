package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraMedicamento extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.30

	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.MEDICAMENTO
	}


	@Override
	BigDecimal getInflator() {
		return INFLATOR
	}
}
