package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

interface Calculadora {

	ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos tabelaDePrecos)

}
