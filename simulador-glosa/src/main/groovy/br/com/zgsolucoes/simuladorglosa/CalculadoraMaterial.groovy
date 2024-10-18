package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraMaterial implements Calculadora {
    @Override
    static BigDecimal calcular(Dado dado, TabelaDePrecos tabelaDePrecos) {
        BigDecimal valorCalculado = dado.valor * 1.20
        return valorCalculado - tabelaDePrecos.valor
    }
}
