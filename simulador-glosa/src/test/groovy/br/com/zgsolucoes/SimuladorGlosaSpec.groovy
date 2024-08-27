package br.com.zgsolucoes

import br.com.zgsolucoes.simuladorglosa.gerador.GeradorDeCriticas
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

@MicronautTest
class SimuladorGlosaSpec extends Specification {

	@Inject
	GeradorDeCriticas geradorDeCriticas

	@Unroll("#arquivoEsperado")
	void 'test gerador de criticas'() {
		given:
		File arquivo = new File(GeradorDeCriticas.getResource('/arquivo_itens.csv').path)

		when:
		String arquivoGerado = geradorDeCriticas.gere(arquivo, formatado)

		then:
		arquivoGerado == GeradorDeCriticas.getResource('/'.concat(arquivoEsperado)).text

		where:
		formatado | arquivoEsperado
		true      | 'gerado_formatado.csv'
		false     | 'gerado.csv'
	}

}
