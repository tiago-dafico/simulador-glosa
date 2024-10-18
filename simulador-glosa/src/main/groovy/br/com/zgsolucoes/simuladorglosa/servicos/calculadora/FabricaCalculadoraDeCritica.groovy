package br.com.zgsolucoes.simuladorglosa.servicos.calculadora

import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import jakarta.inject.Inject

class FabricaCalculadoraDeCritica {

	@Inject
	Collection<ICalculadoraDeCritica> calculadoras

	ICalculadoraDeCritica fabricaCalculadora(TipoItem tipoItem) {
		ICalculadoraDeCritica calculadora = calculadoras.find { it.deveAplicar(tipoItem)}
		if(calculadora == null) {
			throw Exception("Calculadora de crítica não encontrada para o tipo ${tipoItem}")
		}
		return calculadora
	}

}
