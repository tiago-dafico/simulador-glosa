package br.com.zgsolucoes.simuladorglosa.dominio

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

abstract class ICalculadoraDeCritica {
	BigDecimal calculaTipo(TabelaDePrecos tabela) {
		return tabela.valor * getInflator()
	}

	abstract BigDecimal getInflator()



	abstract Boolean deveAplicar(TipoItem tipoItem)
}
