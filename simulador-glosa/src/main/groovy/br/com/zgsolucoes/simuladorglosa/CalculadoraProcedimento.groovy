package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraProcedimento extends CalculadoraPadrao {

	@Override
	ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos itemTabela) {
		ResultadoCalculoItem resultado = super.calcular(dado, itemTabela)
		if (dado.codigo.toString().startsWith('4')) {
			resultado.valorCriticado -= 20
			resultado.valorCalculado -= 20
		}
		return resultado
	}
}
