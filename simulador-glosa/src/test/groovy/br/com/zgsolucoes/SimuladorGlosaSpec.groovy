package br.com.zgsolucoes

import br.com.zgsolucoes.simuladorglosa.gerador.GeradorDeCriticas
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
		File arquivo = new File(GeradorDeCriticas.getResource('/arquivo_itens.csv').path)

		when:
		geradorDeCriticas.gere(arquivo, nomeArquivo, formatado)

		then:
		Paths.get('src/main/resources/gerado', nomeArquivo).toFile().text == GeradorDeCriticas.getResource('/'.concat(arquivoEsperado)).text

		where:
		formatado | nomeArquivo            | arquivoEsperado
		true      | 'gerado_formatado.csv' | 'esperado_formatado.csv'
		false     | 'gerado.csv'           | 'esperado.csv'
	}

}
