package br.com.zgsolucoes

import br.com.zgsolucoes.simuladorglosa.gerador.GeradorDeCriticas
import br.com.zgsolucoes.simuladorglosa.repositorios.TabelaDePrecosRepositorio
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import java.nio.file.Paths

@MicronautTest
class SimuladorGlosaSpec extends Specification {

	@Inject
	GeradorDeCriticas geradorDeCriticas

	@Inject
	TabelaDePrecosRepositorio repositorio

	void 'test gerador de criticas formatando'() {
		given:
		File arquivo = new File(GeradorDeCriticas.getResource('/arquivo_itens.csv').path)

		when:
		repositorio.findAll() >> []
		geradorDeCriticas.gereFormatado(arquivo, nomeArquivo)

		then:
		Paths.get('src/main/resources/gerado', nomeArquivo).toFile().text == GeradorDeCriticas.getResource('/'.concat(arquivoEsperado)).text

		where:
		nomeArquivo            | arquivoEsperado
		'gerado_formatado.csv' | 'esperado_formatado.csv'
	}

	void 'test gerador de criticas sem formatar'() {
		given:
		File arquivo = new File(GeradorDeCriticas.getResource('/arquivo_itens.csv').path)

		when:
		repositorio.findAll() >> []
		geradorDeCriticas.gereSemFormatar(arquivo, nomeArquivo)

		then:
		Paths.get('src/main/resources/gerado', nomeArquivo).toFile().text == GeradorDeCriticas.getResource('/'.concat(arquivoEsperado)).text

		where:
		nomeArquivo  | arquivoEsperado
		'gerado.csv' | 'esperado.csv'
	}


	@MockBean(TabelaDePrecosRepositorio)
	TabelaDePrecosRepositorio TabelaDePrecosRepositorio() {
		return Mock(TabelaDePrecosRepositorio)
	}

}
