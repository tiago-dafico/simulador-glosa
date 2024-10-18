package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraProcedimento extends ICalculadoraDeCritica {
	static final BigDecimal INFLATOR = 1.55

	@Override
	BigDecimal calculaTipo(TipoItem tipoItem, BigDecimal valor) {
		return valor * INFLATOR
	}

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.PROCEDIMENTO
	}
}
