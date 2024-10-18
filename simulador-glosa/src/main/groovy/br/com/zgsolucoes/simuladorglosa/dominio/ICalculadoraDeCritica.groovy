package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

abstract class ICalculadoraDeCritica {
	abstract BigDecimal calculaTipo(ItemFaturado valor)

	abstract Boolean deveAplicar(TipoItem tipoItem)
}
