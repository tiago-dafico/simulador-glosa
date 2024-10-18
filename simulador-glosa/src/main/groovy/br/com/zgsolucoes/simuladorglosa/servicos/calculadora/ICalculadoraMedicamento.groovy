package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraMedicamento extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.30

	final BigDecimal inflator = INFLATOR

	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.MEDICAMENTO
	}

}
