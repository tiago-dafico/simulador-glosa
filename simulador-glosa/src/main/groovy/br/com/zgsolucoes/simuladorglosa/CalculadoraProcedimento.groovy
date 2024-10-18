package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraProcedimento implements Calculadora {
	@Override
	BigDecimal calcular(Dado dado, TabelaDePrecos tabelaDePrecos) {
		BigDecimal valorCalculado = itemTabela.valor * 1.55
		if (dado.codigo.toString().startsWith('4')) {
			valorCalculado -= 20
		}
		return valorCalculado - tabelaDePrecos.valor
	}
}
