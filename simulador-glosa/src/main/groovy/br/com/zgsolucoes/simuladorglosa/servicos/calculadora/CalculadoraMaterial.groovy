package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Order
import jakarta.inject.Singleton

@Order(1)
@CompileStatic
@Singleton
class CalculadoraMaterial extends CalculadoraAbstrataItem {
	static final BigDecimal INFLATOR = 1.20

	final BigDecimal inflator = INFLATOR

	@Override
	Boolean deveAplicar(TipoItem tipoItem) {
		tipoItem == TipoItem.MATERIAL
	}
}
