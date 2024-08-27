package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.ItemTabela
import br.com.zgsolucoes.simuladorglosa.repositorios.ItemTabelaRepositorio
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.text.DecimalFormat
import java.text.NumberFormat

@Singleton
class GeradorDeCriticas {

	private static final Locale LOCALE = new Locale('pt', 'BR')

	@Inject
	ItemTabelaRepositorio itemTabelaRepositorio

	String gere(File arquivo, boolean formatar) {
		List<Map> dados = []
		arquivo.readLines().tail().each {
			Map dado = [:]
			List<String> list = it.tokenize(';')
			dado['codigo'] = list[0]
			dado['tipo'] = list[1]
			dado['valor'] = list[2]
			dados.add(dado)
		}

		List<ItemTabela> tabelaList = itemTabelaRepositorio.findAll()

		List<BigDecimal> calcs = []
		List<BigDecimal> critics = []

		dados.each { Map dado ->
			if (dado.tipo == 'Procedimento') {
				ItemTabela itemTabela = tabelaList.find {
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
				ItemTabela itemTabela = tabelaList.find {
					it.codigo == dado.codigo
				}
				BigDecimal calc = itemTabela.valor * 1.20
				BigDecimal valor = dado.valor.toString().toBigDecimal()
				BigDecimal critic = calc - valor
				calcs.add(calc)
				critics.add(critic)
			} else if (dado.tipo == 'Medicamento') {
				ItemTabela itemTabela = tabelaList.find {
					it.codigo == dado.codigo
				}
				BigDecimal calc = itemTabela.valor * 1.30
				BigDecimal valor = dado.valor.toString().toBigDecimal()
				BigDecimal critic = calc - valor
				calcs.add(calc)
				critics.add(critic)
			} else if (dado.tipo == 'Taxa') {
				ItemTabela itemTabela = tabelaList.find {
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

		imprima('arquivo.csv', dados, calcs, critics, formatar)

	}

	String imprima(
			String nome,
			List<Map> dados,
			List<BigDecimal> calcs,
			List<BigDecimal> critics,
			boolean formatar = true
	) {
		File file = new File(this.class.classLoader.getResource('gerado').path +nome)
		if(!file.exists()) {
			file.createNewFile()
		}
		String texto = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'
		for (int i =0 ; i< dados.size(); i++) {
			Map dado = dados[i]
			BigDecimal calc = calcs[i]
			BigDecimal critic = critics[i]
			if(formatar) {
				final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)
				texto += dado.codigo
				texto += ';'
				texto += ''
				texto += CURRENCY_FORMAT.format(calc)
				texto += ';'
				texto += CURRENCY_FORMAT.format(critic)
				texto += '\n'
			} else {
				texto += dado.codigo
				texto += ';'
				texto += calc
				texto += ';'
				texto += critic
				texto += '\n'
			}
		}
		file.write(texto)
		return texto
	}

}
