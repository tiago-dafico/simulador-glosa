package br.com.zgsolucoes.simuladorglosa.servicos.impressora

import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@CompileStatic
class FabricaImpressoraAbstrato {

	@Inject
	Collection<ImpressoraAbstrata> calculadoras

	ImpressoraAbstrata fabricaCalculadora(Boolean podeImprimir) {
		ImpressoraAbstrata calculadora = calculadoras.find { it.podeImprimir(podeImprimir)}
		if(!calculadora) {
			throw new IllegalArgumentException("Erro") //@Todo Ver quais erros possiveis
		}
		return calculadora
	}

}
