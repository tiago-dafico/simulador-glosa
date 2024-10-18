package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.ItemTabela
import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import groovy.transform.CompileStatic
import jakarta.inject.Singleton

@CompileStatic
@Singleton
abstract class CalculadoraAbstrataItem {

	BigDecimal calculaValorTabela(ItemTabela tabela) {
		return tabela.valor * getInflator()
	}

	abstract BigDecimal getInflator()

	abstract Boolean deveAplicar(TipoItem tipoItem)

}
