package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraTaxa {

	static ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos itemTabela) {
		BigDecimal valorCalculado = itemTabela.valor * 1.15
		if (dado.codigo.toString().startsWith('4')) {
			valorCalculado -= 20
		}
		return new ResultadoCalculoItem(valorCalculado: valorCalculado, valorCriticado: valorCalculado - dado.valor )
	}
}
