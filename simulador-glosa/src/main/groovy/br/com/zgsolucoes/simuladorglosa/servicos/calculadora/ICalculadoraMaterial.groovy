package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraMaterial extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.20

	final BigDecimal inflator = INFLATOR

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.MATERIAL
	}
}
