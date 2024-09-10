package br.com.zgsolucoes.simuladorglosa.gerador

import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.dtos.ValorAnalisado
import br.com.zgsolucoes.simuladorglosa.dtos.ValorFaturado
import br.com.zgsolucoes.simuladorglosa.repositorios.TabelaDePrecosRepositorio
import br.com.zgsolucoes.simuladorglosa.servicos.FabricaCalculador
import br.com.zgsolucoes.simuladorglosa.servicos.arquivo.ImpressorCSV
import br.com.zgsolucoes.simuladorglosa.servicos.arquivo.ImpressorCSVFormatado
import br.com.zgsolucoes.simuladorglosa.servicos.arquivo.LeitorCSV
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

@CompileStatic
@Singleton
class GeradorDeCriticas {

	private static final Locale LOCALE = new Locale('pt', 'BR')

	@Inject
	TabelaDePrecosRepositorio itemTabelaRepositorio

	@Inject
	FabricaCalculador fabricaCalculador

	void gereSemFormatar(File arquivo, String nomeArquivo) {
		gere(arquivo, nomeArquivo) { List<ValorAnalisado> analisados ->
			ImpressorCSV.imprimir(nomeArquivo, analisados)
		}
	}

	void gereFormatado(File arquivo, String nomeArquivo) {
		gere(arquivo, nomeArquivo) { List<ValorAnalisado> analisados ->
			ImpressorCSVFormatado.imprimir(nomeArquivo, analisados)
		}
	}

	void gere(File arquivo, String nomeArquivo, Closure impressao) {
		List<ValorFaturado> faturados = LeitorCSV.obterFaturados(arquivo)
		List<TabelaDePrecos> tabelaList = itemTabelaRepositorio.findAll()
		List<ValorAnalisado> analisados = calculeFaturados(faturados, tabelaList)
		impressao.call(analisados)
	}

	List<ValorAnalisado> calculeFaturados(List<ValorFaturado> faturados, List<TabelaDePrecos> tabelas) {
		List<ValorAnalisado> analisados = faturados.collect { ValorFaturado faturado ->
			TabelaDePrecos itemTabela = obterTabela(tabelas, faturado.codigo)
			return analisarFaturado(faturado, itemTabela)
		}
		return analisados
	}

	ValorAnalisado analisarFaturado(ValorFaturado faturado, TabelaDePrecos itemTabela) {
		return fabricaCalculador.encontrar(faturado.tipo)
				.calcular(faturado, itemTabela)
	}

	static TabelaDePrecos obterTabela(List<TabelaDePrecos> tabelas, String codigo) {
		Optional.ofNullable(tabelas.find {
			it.codigo == codigo
		}).orElseThrow {
			throw new UnsupportedOperationException("Não existe tabela de preços para este codigo")
		}
	}

}
