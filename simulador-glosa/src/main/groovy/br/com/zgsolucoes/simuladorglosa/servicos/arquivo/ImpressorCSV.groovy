package br.com.zgsolucoes.simuladorglosa.servicos.arquivo

import br.com.zgsolucoes.simuladorglosa.dtos.ValorAnalisado
import groovy.transform.CompileStatic

import java.nio.file.Files
import java.nio.file.Paths

@CompileStatic
class ImpressorCSV {

    protected static final String SEPARADOR = ';'
    protected static final String QUEBRA_LINHA = '\n'
    protected static final String SAVE_PATH = 'src/main/resources/gerado'

    static void imprimir(String nomeArquivo, List<ValorAnalisado> analisados) {
        String texto = criarLinhasTexto(analisados)
                .join(QUEBRA_LINHA)
                .concat(QUEBRA_LINHA)
        Files.writeString(Paths.get(SAVE_PATH, nomeArquivo), texto)
    }

    protected static String criarLinha(ValorAnalisado analisado) {
        return [
                analisado.faturado.codigo,
                analisado.faturado.valor,
                analisado.calculado,
                analisado.criticado
        ]*.toString().join(SEPARADOR)
    }

    protected static List<String> criarLinhasTexto(List<ValorAnalisado> analisados) {
        List<String> linhas = [obterLinhaTitulo(['Código', 'Valor faturado', 'Valor Calculado', 'Valor criticado'])]
        linhas.addAll(analisados.collect { criarLinha(it) })
        return linhas
    }

    protected static String obterLinhaTitulo(List<String> campos) {
        campos.join(SEPARADOR)
    }

}
