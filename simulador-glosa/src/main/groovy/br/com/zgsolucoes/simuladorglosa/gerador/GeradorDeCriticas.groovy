package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.Dado
import br.com.zgsolucoes.simuladorglosa.dominio.ItemType
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
		List<Dado> dados = []
		arquivo.readLines().tail().each {
			List<String> list = it.tokenize(';')
			Dado dado = new Dado(
					codigo: list[0],
					tipo: ItemType.valueOf(list[1].toUpperCase()),
					valor: BigDecimal.valueOf(list[2].toLong())
			)
			dados.add(dado)
		}

		List<TabelaDePrecos> tabelaList = itemTabelaRepositorio.findAll()

		List<BigDecimal> calcs = []
		List<BigDecimal> critics = []

		dados.each { Dado dado ->
			TabelaDePrecos itemTabela = tabelaList.find {
				it.codigo == dado.codigo
			}
			BigDecimal calc = itemTabela.valor * dado.getTipo().getValue()
			BigDecimal valor = dado.valor.toString().toBigDecimal()
			BigDecimal critic = calc - valor

			if (dado dado.codigo.toString().startsWith('4')) {
				calc -= 20
			}

			calcs.add(calc)
			critics.add(critic)
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
		for (int i = 0; i < dados.size(); i++) {
			Map dado = dados[i]
			BigDecimal calc = calcs[i]
			BigDecimal critic = critics[i]
			if (formatar) {
				formataValoresDoTexto(texto, dado.codigo, calc, critic)
			} else {
				textoSemFormatacao(texto, dado.codigo, calc, critic)
			}
		}

		Files.writeString(Paths.get('src/main/resources/gerado', nome), texto)
	}

	String formataValoresDoTexto(String texto, Dado dado, BigDecimal calc, BigDecimal critic) {
		final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)
		texto += dado.codigo
		texto += ';'
		texto += CURRENCY_FORMAT.format(dado.valor.toString().toBigDecimal())
		texto += ';'
		texto += CURRENCY_FORMAT.format(calc)
		texto += ';'
		texto += CURRENCY_FORMAT.format(critic)
		texto += '\n'
		return texto
	}

	String textoSemFormatacao(String texto, Dado dado, BigDecimal calc, BigDecimal critic) {
		texto += dado.codigo
		texto += ';'
		texto += dado.valor
		texto += ';'
		texto += calc.setScale(2)
		texto += ';'
		texto += critic.setScale(2)
		texto += '\n'
		return texto
	}

}
