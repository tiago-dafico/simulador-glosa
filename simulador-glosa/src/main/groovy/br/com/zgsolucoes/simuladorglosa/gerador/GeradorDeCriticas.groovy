package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado
import br.com.zgsolucoes.simuladorglosa.dominio.ItemFaturado
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import br.com.zgsolucoes.simuladorglosa.repositorios.TabelaDePrecosRepositorio
import br.com.zgsolucoes.simuladorglosa.servicos.calculadora.FabricaCalculadoraItemAbstrato
import br.com.zgsolucoes.simuladorglosa.servicos.calculadora.CalculadoraAbstrataItem
import br.com.zgsolucoes.simuladorglosa.servicos.impressora.FabricaImpressoraAbstrato
import br.com.zgsolucoes.simuladorglosa.servicos.impressora.ImpressoraAbstrata
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

	@Inject
	FabricaCalculadoraItemAbstrato fabricaCalculadoraDeCritica

	@Inject
	FabricaImpressoraAbstrato fabricaImpressoraAbstrato

	List<ItemFaturado> extraiItensDoArquivo(File arquivo) {
		List<ItemFaturado> itensFaturados = []
		arquivo.readLines().tail().each {
			ItemFaturado itemFaturado = [:]
			List<String> list = it.tokenize(';')
			itemFaturado.codigo = list[0]
			itemFaturado.tipo = TipoItem.valueOf(list[1].toUpperCase())
			itemFaturado.valor = list[2] as BigDecimal
			itensFaturados.add(itemFaturado)
		}

		return itensFaturados
	}

	void gere(File arquivo, String nomeArquivo, boolean formatar) {
		List<ItemFaturado> itensFaturados = extraiItensDoArquivo(arquivo)
		List<TabelaDePrecos> tabelaList = itemTabelaRepositorio.findAll()

		List<ItemCriticado> itemsCriticado = []

		itensFaturados.each { ItemFaturado itemFaturado ->
			CalculadoraAbstrataItem calculadora = fabricaCalculadoraDeCritica.fabricaCalculadora(itemFaturado.tipo)
			// TODO Talvez um intermediário
			TabelaDePrecos itemTabela = tabelaList.find {
				it.codigo == itemFaturado.codigo
			}

			BigDecimal valorCalculado = calculadora.calculaValorTabela(itemTabela)

			itemsCriticado.add(
					new ItemCriticado().tap {
						codigo = itemFaturado.codigo
						valorFaturado = itemFaturado.valor
						it.valorCalculado = valorCalculado
						valorCriticado = valorCalculado - itemFaturado.valor
					}
			)
		}

		imprima(nomeArquivo, itemsCriticado, formatar)

	}

	// TODO refatorar parte de importação de dados
	void imprima(
			String nome,
			List<ItemCriticado> itemsCriticado,
			boolean formatar = true
	) {
		String texto = 'Código;Valor faturado;Valor Calculado;Valor criticado\n'
		for (int i = 0; i < itemsCriticado.size(); i++) {
			ItemCriticado itemCriticado = itemsCriticado[i]

			ImpressoraAbstrata impressora = fabricaImpressoraAbstrato.fabricaCalculadora(formatar)
			impressora.montaImpressao(itemCriticado, texto)
			if (formatar) {
				final DecimalFormat CURRENCY_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE)
				texto += itemCriticado.codigo
				texto += ';'
				texto += CURRENCY_FORMAT.format(valorFaturado)
				texto += ';'
				texto += CURRENCY_FORMAT.format(valorCalculado)
				texto += ';'
				texto += CURRENCY_FORMAT.format(valorCriticado)
				texto += '\n'
			} else {
				texto += itemCriticado.codigo
				texto += ';'
				texto += valorFaturado
				texto += ';'
				texto += valorCalculado.setScale(2)
				texto += ';'
				texto += valorCriticado.setScale(2)
				texto += '\n'
			}
		}

		Files.writeString(Paths.get('src/main/resources/gerado', nome), texto)
	}

}
