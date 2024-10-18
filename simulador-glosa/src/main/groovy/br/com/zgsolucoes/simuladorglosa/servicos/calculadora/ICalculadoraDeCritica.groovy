package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem

abstract class ICalculadoraDeCritica {
	BigDecimal calculaValorTabela(TabelaDePrecos tabela) {
		return tabela.valor * getInflator()
	}

	abstract BigDecimal getInflator()

	abstract Boolean deveAplicar(TipoItem tipoItem)
}
