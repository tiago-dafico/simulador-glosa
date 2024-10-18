package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraMaterial implements Calculadora {
	@Override
	BigDecimal calcular(Dado dado, TabelaDePrecos tabelaDePrecos) {
		BigDecimal calc = itemTabela.valor * 1.20

		BigDecimal valorCalculado = itemTabela.valor * 1.55
		return valorCalculado - tabelaDePrecos.valor
	}
}
