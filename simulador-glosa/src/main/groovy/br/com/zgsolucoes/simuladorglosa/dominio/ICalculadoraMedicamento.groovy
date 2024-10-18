package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraMedicamento extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.30

	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.MEDICAMENTO
	}

	@Override
	BigDecimal calculaTipo(TipoItem tipoItem, BigDecimal valor) {
		if (deveAplicar(tipoItem)) {
			return valor * INFLATOR
		}
	}
}
