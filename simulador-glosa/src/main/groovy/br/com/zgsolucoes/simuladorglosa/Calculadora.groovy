package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

interface Calculadora {

	BigDecimal calcular(Dado dado, TabelaDePrecos tabelaDePrecos)

}
