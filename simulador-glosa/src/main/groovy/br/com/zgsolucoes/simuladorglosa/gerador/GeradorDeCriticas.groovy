package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.Item
import br.com.zgsolucoes.simuladorglosa.dominio.TipoItem
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
		List<Item> items = arquivo.readLines().tail().collect {
			List<String> list = it.tokenize(';')
			return new Item(
					codigo: list[0],
					tipo: TipoItem.valueOf(list[1].toUpperCase()),
					valor: BigDecimal.valueOf(list[2].toLong())
			)
		}

		List<TabelaDePrecos> tabelaList = itemTabelaRepositorio.findAll()

		List<BigDecimal> calcs = []
		List<BigDecimal> critics = []

		items.each { Item item ->
			TabelaDePrecos itemTabela = tabelaList.find {
				it.codigo == item.codigo
			}
			BigDecimal calc = itemTabela.valor * item.getTipo().getValue()
			BigDecimal valor = item.valor
			BigDecimal critic = calc - valor

			if (item.codigo.startsWith('4') && item) {
				calc -= 20
			}

			calcs.add(calc)
			critics.add(critic)
		}

		imprima(nomeArquivo, items, calcs, critics, formatar)

	}

	void imprima(
			String nome,
			List<Item> items,
			List<BigDecimal> calcs,
			List<BigDecimal> critics,
			boolean formatar = true
	) {
		String texto = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'
		for (int i = 0; i < items.size(); i++) {
			Item item = items[i]
			BigDecimal calc = calcs[i]
			BigDecimal critic = critics[i]
			if (formatar) {
				textoComFormatacao(texto, item, calc, critic)
			} else {
				textoSemFormatacao(texto, item, calc, critic)
			}
		}

		Files.writeString(Paths.get('src/main/resources/gerado', nome), texto)
	}

	static String formataValoresDoTexto(BigDecimal valor) {
		final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)
		return CURRENCY_FORMAT.format(valor)
	}
	static String textoComFormatacao(String texto, Item item, BigDecimal calc, BigDecimal critic) {
		texto += item.codigo
		texto += ';'
		texto += formataValoresDoTexto(item.valor)
		texto += ';'
		texto += formataValoresDoTexto(calc)
		texto += ';'
		texto += formataValoresDoTexto(critic)
		texto += '\n'
		return texto
	}

	static String textoSemFormatacao(String texto, Item item, BigDecimal calc, BigDecimal critic) {
		texto += item.codigo
		texto += ';'
		texto += item.valor
		texto += ';'
		texto += calc.setScale(2)
		texto += ';'
		texto += critic.setScale(2)
		texto += '\n'
		return texto
	}

}
