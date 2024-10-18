package br.com.zgsolucoes.simuladorglosa.fabricas

import br.com.zgsolucoes.simuladorglosa.servicos.impressora.ImpressoraAbstrata
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@CompileStatic
class FabricaImpressoraAbstrato {

	@Inject
	Collection<ImpressoraAbstrata> impressoras

	ImpressoraAbstrata fabricaImpressora(Boolean podeImprimir) {
		ImpressoraAbstrata impressora = impressoras.find { it.podeImprimir(podeImprimir) }
		if(!impressora) {
			throw new IllegalArgumentException('Impressora não encontrada')
		}
		return impressora
	}

}
