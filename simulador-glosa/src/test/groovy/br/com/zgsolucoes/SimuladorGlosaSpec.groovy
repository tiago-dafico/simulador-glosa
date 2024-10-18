package br.com.zgsolucoes

import br.com.zgsolucoes.simuladorglosa.Dado
import br.com.zgsolucoes.simuladorglosa.dominio.TipoItem
import br.com.zgsolucoes.simuladorglosa.gerador.GeradorDeCriticas
import br.com.zgsolucoes.simuladorglosa.repositorios.Integrador
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll
import java.nio.file.Paths

@MicronautTest
class SimuladorGlosaSpec extends Specification {

	@Inject
	GeradorDeCriticas geradorDeCriticas

	@Unroll("#arquivoEsperado")
	void 'test gerador de criticas'() {
		given:

		Integrador integradorCsv = new Integrador() {

			@Override
			List<Dado> busque() {
				File arquivo = new File(GeradorDeCriticas.getResource('/arquivo_itens.csv').path)
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
		when:
		geradorDeCriticas.gere(integradorCsv, nomeArquivo, formatado)

		then:
		Paths.get('src/main/resources/gerado', nomeArquivo).toFile().text == GeradorDeCriticas.getResource('/'.concat(arquivoEsperado)).text

		where:
		formatado | nomeArquivo            | arquivoEsperado
		true      | 'gerado_formatado.csv' | 'esperado_formatado.csv'
		false     | 'gerado.csv'           | 'esperado.csv'
	}

}
