package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraMedicamento implements Calculadora {
	@Override
	static BigDecimal calcular(Dado dado, TabelaDePrecos tabelaDePrecos) {
		BigDecimal valorCalculado = dado.valor * 1.30
		if (dado.codigo.toString().startsWith('4')) {
			valorCalculado -= 20
		}
		return valorCalculado - tabelaDePrecos.valor
	}
}
