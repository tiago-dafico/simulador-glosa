package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraProcedimento  {

	static ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos tabelaDePrecos) {
		BigDecimal valorCalculado = dado.valor * 1.55
		if (dado.codigo.toString().startsWith('4')) {
			valorCalculado -= 20
		}
		return new ResultadoCalculoItem(valorCalculado: valorCalculado, valorCriticado: valorCalculado - tabelaDePrecos.valor )
	}
}
