package br.com.zgsolucoes.simuladorglosa.servicos.arquivo


import br.com.zgsolucoes.simuladorglosa.dtos.ValorAnalisado
import groovy.transform.CompileStatic
import java.text.DecimalFormat
import java.text.NumberFormat

@CompileStatic
class ImpressorCSVFormatado extends ImpressorCSV {

    private static final Locale LOCALE = new Locale('pt', 'BR')
    private static final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)

    protected static String criarLinha(ValorAnalisado analisado) {
        return [
                analisado.faturado.codigo,
                CURRENCY_FORMAT.format(analisado.faturado.valor),
                CURRENCY_FORMAT.format(analisado.calculado),
                CURRENCY_FORMAT.format(analisado.criticado)
        ].join(SEPARADOR)
    }

}
