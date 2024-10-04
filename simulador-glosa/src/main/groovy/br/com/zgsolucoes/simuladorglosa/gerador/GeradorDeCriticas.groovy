package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
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
		List<Map> dados = []
		arquivo.readLines().tail().each {
			Map dado = [:]
			List<String> list = it.tokenize(';')
			dado['codigo'] = list[0]
			dado['tipo'] = list[1]
			dado['valor'] = list[2]
			dados.add(dado)
		}

		List<TabelaDePrecos> tabelaList = itemTabelaRepositorio.findAll()

		List<BigDecimal> calcs = []
		List<BigDecimal> critics = []

		dados.each { Map dado ->
			if (dado.tipo == 'Procedimento') {
				TabelaDePrecos itemTabela = tabelaList.find {
					it.codigo == dado.codigo
				}
				BigDecimal calc = itemTabela.valor * 1.55
				BigDecimal valor = dado.valor.toString().toBigDecimal()
				BigDecimal critic = calc - valor
				if (dado.codigo.toString().startsWith('4')) {
					calc -= 20
				}
				calcs.add(calc)
				critics.add(critic)
			} else if (dado.tipo == 'Material') {
				TabelaDePrecos itemTabela = tabelaList.find {
					it.codigo == dado.codigo
				}
				BigDecimal calc = itemTabela.valor * 1.20
				BigDecimal valor = dado.valor.toString().toBigDecimal()
				BigDecimal critic = calc - valor
				calcs.add(calc)
				critics.add(critic)
			} else if (dado.tipo == 'Medicamento') {
				TabelaDePrecos itemTabela = tabelaList.find {
					it.codigo == dado.codigo
				}
				BigDecimal calc = itemTabela.valor * 1.30
				BigDecimal valor = dado.valor.toString().toBigDecimal()
				BigDecimal critic = calc - valor
				calcs.add(calc)
				critics.add(critic)
			} else if (dado.tipo == 'Taxa') {
				TabelaDePrecos itemTabela = tabelaList.find {
					it.codigo == dado.codigo
				}
				BigDecimal calc = itemTabela.valor * 1.15
				BigDecimal valor = dado.valor.toString().toBigDecimal()
				BigDecimal critic = calc - valor
				calcs.add(calc)
				critics.add(critic)
			} else {
				throw new Exception('Não é de nenhum tipo')
			}
		}

		imprima(nomeArquivo, dados, calcs, critics, formatar)

	}

	void imprima(
			String nome,
			List<Map> dados,
			List<BigDecimal> calcs,
			List<BigDecimal> critics,
			boolean formatar = true
	) {
		String texto = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'
		for (int i =0 ; i< dados.size(); i++) {
			Map dado = dados[i]
			BigDecimal calc = calcs[i]
			BigDecimal critic = critics[i]
			if(formatar) {
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
				texto += calc.setScale(2)
				texto += ';'
				texto += critic.setScale(2)
				texto += '\n'
			}
		}

		Files.writeString(Paths.get('src/main/resources/gerado',nome),texto)
	}

}
