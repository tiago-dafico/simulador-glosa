package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraTaxa implements ICalculadoraDeCritica{
	static final BigDecimal INFLATOR = 1.15

	@Override
	BigDecimal calculaTipo(TipoItem tipoItem, BigDecimal valor) {
		return valor * INFLATOR
	}
}
