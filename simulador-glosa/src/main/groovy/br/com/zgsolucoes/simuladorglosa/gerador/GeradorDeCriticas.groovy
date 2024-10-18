package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.ItemCriticado
import br.com.zgsolucoes.simuladorglosa.dominio.ItemFaturado
import br.com.zgsolucoes.simuladorglosa.dominio.ItemTabela
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dominio.enums.TipoItem
import br.com.zgsolucoes.simuladorglosa.repositorios.TabelaDePrecosRepositorio
import br.com.zgsolucoes.simuladorglosa.servicos.calculadora.CalculadoraAbstrataItem
import br.com.zgsolucoes.simuladorglosa.fabricas.FabricaCalculadoraItemAbstrato
import br.com.zgsolucoes.simuladorglosa.fabricas.FabricaImpressoraAbstrato
import br.com.zgsolucoes.simuladorglosa.servicos.impressora.ImpressoraAbstrata
import br.com.zgsolucoes.simuladorglosa.servicos.tabela_precos.ServicoTabelaDePrecos
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.nio.file.Files
import java.nio.file.Paths

@Singleton
class GeradorDeCriticas {

	@Inject
	ServicoTabelaDePrecos servicoTabelaDePrecos

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

		List<ItemCriticado> itemsCriticado = []

		itensFaturados.each { ItemFaturado itemFaturado ->
			CalculadoraAbstrataItem calculadora = fabricaCalculadoraDeCritica.fabricaCalculadora(itemFaturado.tipo)

			ItemTabela itemTabela = servicoTabelaDePrecos.findByCodigo(itemFaturado.codigo)
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

	void imprima(
			String nome,
			List<ItemCriticado> itemsCriticado,
			boolean formatar = true
	) {
		ImpressoraAbstrata impressora = fabricaImpressoraAbstrato.fabricaImpressora(formatar)
		String texto = impressora.montaImpressaoTodos(itemsCriticado)
		Files.writeString(Paths.get('src/main/resources/gerado', nome), texto)
	}

}
