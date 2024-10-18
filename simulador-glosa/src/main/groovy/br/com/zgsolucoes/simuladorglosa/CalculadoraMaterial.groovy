package br.com.zgsolucoes.simuladorglosa

import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos

class CalculadoraMaterial implements Calculadora{

    @Override
    ResultadoCalculoItem calcular(Dado dado, TabelaDePrecos itemTabela) {
        BigDecimal valorCalculado = itemTabela.valor * 1.20
        return new ResultadoCalculoItem(valorCalculado: valorCalculado, valorCriticado: valorCalculado - dado.valor )
    }
}
