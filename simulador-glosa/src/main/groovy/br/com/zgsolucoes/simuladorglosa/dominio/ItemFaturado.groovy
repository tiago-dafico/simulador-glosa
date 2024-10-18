package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import groovy.transform.CompileStatic

@CompileStatic
class ItemFaturado {

	String codigo
	TipoItem tipo
	BigDecimal valor
}
