package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

interface ICalculadoraDeCritica {
	BigDecimal calculaTipo(TipoItem tipoItem)

}
