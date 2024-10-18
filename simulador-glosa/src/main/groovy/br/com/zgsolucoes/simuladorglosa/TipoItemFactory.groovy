package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TipoItem

class TipoItemFactory {

	Calculadora getCalculadora(TipoItem tipoItem) {

		if (tipoItem == TipoItem.PROCEDIMENTO) {
			return new CalculadoraProcedimento()
		} else if (tipoItem == TipoItem.MATERIAL) {
			return new CalculadoraMaterial()
		} else if (tipoItem == TipoItem.MEDICAMENTO) {
			return new CalculadoraMedicamento()
		} else if (tipoItem == TipoItem.TAXA) {
			return new CalculadoraTaxa()
		} else {
			throw new Exception('Não é de nenhum tipo')
		}
	}
}
