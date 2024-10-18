package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import groovy.transform.Undefined

class CalculadoraMaterial implements ICalculadoraDeCritica{

	static final BigDecimal INFLATOR = 1.20

	@Override
	BigDecimal calculaTipo(TipoItem tipoItem, BigDecimal valor) {
		return valor * INFLATOR
	}
}
