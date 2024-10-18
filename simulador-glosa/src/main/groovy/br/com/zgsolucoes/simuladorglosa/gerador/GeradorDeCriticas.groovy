package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.CalculadoraMaterial
import br.com.zgsolucoes.simuladorglosa.CalculadoraMedicamento
import br.com.zgsolucoes.simuladorglosa.CalculadoraProcedimento
import br.com.zgsolucoes.simuladorglosa.CalculadoraTaxa
import br.com.zgsolucoes.simuladorglosa.Dado
import br.com.zgsolucoes.simuladorglosa.dominio.ResultadoCalculoItem
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dominio.TipoItem
import br.com.zgsolucoes.simuladorglosa.repositorios.TabelaDePrecosRepositorio
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat
import java.text.NumberFormat

@Singleton
class GeradorDeCriticas {

    private static final Locale LOCALE = new Locale('pt', 'BR')

    @Inject
    TabelaDePrecosRepositorio itemTabelaRepositorio

    void gere(File arquivo, String nomeArquivo, boolean formatar) {
        List<Dado> dados = dataParse(arquivo)

        List<TabelaDePrecos> tabelaList = itemTabelaRepositorio.findAll()

        List<ResultadoCalculoItem> resultados = []
        List<BigDecimal> critics = []

        dados.each { Dado dado ->

            if (dado.tipo == TipoItem.PROCEDIMENTO) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                ResultadoCalculoItem calc = CalculadoraProcedimento.calcular(dado, itemTabela)
                resultados.add(calc)
            } else if (dado.tipo == TipoItem.MATERIAL) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                ResultadoCalculoItem calc = CalculadoraMaterial.calcular(dado, itemTabela)
                resultados.add(calc)
            } else if (dado.tipo == TipoItem.MEDICAMENTO) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                ResultadoCalculoItem calc = CalculadoraMedicamento.calcular(dado, itemTabela)
                resultados.add(calc)
            } else if (dado.tipo == TipoItem.TAXA) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                ResultadoCalculoItem calc = CalculadoraTaxa.calcular(dado, itemTabela)
                resultados.add(calc)
            } else {
                throw new Exception('Não é de nenhum tipo')
            }
        }

        imprima(nomeArquivo, dados, resultados, formatar)

    }

    void imprima(
            String nome,
            List<Dado> dados,
            List<ResultadoCalculoItem> resultados,
            boolean formatar = true
    ) {
        String texto = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'
        for (int i = 0; i < dados.size(); i++) {
            Dado dado = dados[i]
            ResultadoCalculoItem calc = resultados[i]
            if (formatar) {
                final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)
                texto += dado.codigo
                texto += ';'
                texto += CURRENCY_FORMAT.format(dado.valor.toString().toBigDecimal())
                texto += ';'
                texto += CURRENCY_FORMAT.format(calc.valorCalculado)
                texto += ';'
                texto += CURRENCY_FORMAT.format(calc.valorCriticado)
                texto += '\n'
            } else {
                texto += dado.codigo
                texto += ';'
                texto += dado.valor
                texto += ';'
                texto += calc.valorCalculado.round(2)
                texto += ';'
                texto += calc.valorCriticado.round(2)
                texto += '\n'
            }
        }

        Files.writeString(Paths.get('src/main/resources/gerado', nome), texto)
    }

    private static List<Dado> dataParse(File arquivo) {
        List<Dado> dados = []
        arquivo.readLines().tail().each {
            List<String> list = it.tokenize(';')
            Dado dado = new Dado(
                    codigo: list[0],
                    tipo: TipoItem.fromValor(list[1]),
                    valor: list[2].toLong(),
            )
            dados.add(dado)
        }
        return dados
    }
}
