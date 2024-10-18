package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraMaterial {

    static ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos tabelaDePrecos) {
        BigDecimal valorCalculado = dado.valor * 1.20
        return new ResultadoCalculoItem(valorCalculado: valorCalculado, valorCriticado: valorCalculado - tabelaDePrecos.valor )
    }
}
