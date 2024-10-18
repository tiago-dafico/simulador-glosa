package br.com.zgsolucoes.simuladorglosa.fabricas

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import br.com.zgsolucoes.simuladorglosa.servicos.calculadora.CalculadoraAbstrataItem
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@CompileStatic
class FabricaCalculadoraItemAbstrato {

	@Inject
	Collection<CalculadoraAbstrataItem> calculadoras

	CalculadoraAbstrataItem fabricaCalculadora(TipoItem tipoItem) {
		CalculadoraAbstrataItem calculadora = calculadoras.find { it.deveAplicar(tipoItem)}
		if(!calculadora) {
			throw new IllegalArgumentException("Calculadora de crítica não encontrada para o tipo ${tipoItem}")
		}
		return calculadora
	}

}
