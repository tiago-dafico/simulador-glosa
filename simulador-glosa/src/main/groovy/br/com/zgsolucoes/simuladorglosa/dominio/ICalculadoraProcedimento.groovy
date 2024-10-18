package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

class ICalculadoraProcedimento implements ICalculadoraDeCritica{
	static final BigDecimal MULTIPLICADOR = 1.55

	@Override
	BigDecimal calculaTipo(TipoItem tipoItem) {
		return null
	}
}
