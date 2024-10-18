package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraPadrao implements Calculadora {

	@Override
	ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos itemTabela) {
		BigDecimal valorCalculado = itemTabela.valor * dado.tipo.multiplicador
		return new ResultadoCalculoItem(valorCalculado: valorCalculado, valorCriticado: valorCalculado - dado.valor )
	}
}
