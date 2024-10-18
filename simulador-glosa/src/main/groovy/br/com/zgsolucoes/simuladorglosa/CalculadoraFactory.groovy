package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TipoItem

class CalculadoraFactory {

	static Calculadora getCalculadora(TipoItem tipoItem) {
		if (tipoItem == TipoItem.PROCEDIMENTO) {
			return new CalculadoraProcedimento()
		} else {
			return new CalculadoraPadrao()
		}
	}
}
