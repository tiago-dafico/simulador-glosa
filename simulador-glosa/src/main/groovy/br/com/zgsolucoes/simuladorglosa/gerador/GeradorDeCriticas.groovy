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

        dados.each { Map dado ->

            if (dado.tipo == TipoItem.PROCEDIMENTO) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                BigDecimal calc = CalculadoraProcedimento.calcular(dado, itemTabela)
                resultados.add(new ResultadoCalculoItem(calc, critic))
            } else if (dado.tipo == TipoItem.MATERIAL) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                BigDecimal calc = CalculadoraMaterial.calcular(dado, itemTabela)
                resultados.add(calc)
            } else if (dado.tipo == TipoItem.MEDICAMENTO) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                BigDecimal calc = CalculadoraMedicamento.calcular(dado, itemTabela)
                resultados.add(calc)
            } else if (dado.tipo == TipoItem.TAXA) {
                TabelaDePrecos itemTabela = tabelaList.find {
                    it.codigo == dado.codigo
                }
                BigDecimal calc = CalculadoraTaxa.calcular(dado, itemTabela)
                resultados.add(calc)
            } else {
                throw new Exception('Não é de nenhum tipo')
            }
        }

        imprima(nomeArquivo, dados, resultados, formatar)

    }

    void imprima(
            String nome,
            List<Map> dados,
            List<BigDecimal> resultados,
            boolean formatar = true
    ) {
        String texto = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'
        for (int i = 0; i < dados.size(); i++) {
            Map dado = dados[i]
            BigDecimal calc = resultados[i]
            if (formatar) {
                final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)
                texto += dado.codigo
                texto += ';'
                texto += CURRENCY_FORMAT.format(dado.valor.toString().toBigDecimal())
                texto += ';'
                texto += CURRENCY_FORMAT.format(calc)
                texto += ';'
                texto += CURRENCY_FORMAT.format(critic)
                texto += '\n'
            } else {
                texto += dado.codigo
                texto += ';'
                texto += dado.valor
                texto += ';'
                texto += calc.round(2)
                texto += ';'
                texto += critic.round(2)
                texto += '\n'
            }
        }

        Files.writeString(Paths.get('src/main/resources/gerado', nome), texto)
    }

    private List<Dado> dataParse(File arquivo) {
        List<Dado> dados = []
        arquivo.readLines().tail().each {
            List<String> list = it.tokenize(';')
            Dado dado = new Dado(
                    codigo: list[0],
                    tipo: TipoItem.valueOf(list[1]),
                    valor: list[2].toLong(),
            )
            dados.add(dado)
        }
        return dados
    }
}
