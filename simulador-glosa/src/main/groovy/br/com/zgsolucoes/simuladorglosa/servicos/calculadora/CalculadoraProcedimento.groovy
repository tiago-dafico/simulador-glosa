package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Order
import jakarta.inject.Singleton

@Order(1)
@CompileStatic
@Singleton
class CalculadoraProcedimento extends CalculadoraAbstrataItem {
	static final BigDecimal INFLATOR = 1.55

	final BigDecimal inflator = INFLATOR

	@Override
	BigDecimal calculaValorTabela(TabelaDePrecos itemTabela) {

		BigDecimal desconto = 0

		if (itemTabela.codigo.toString().startsWith('4')) {
			desconto = 20
		}

		return itemTabela.valor * INFLATOR - desconto
	}

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.PROCEDIMENTO
	}
}
