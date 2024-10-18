package br.com.zgsolucoes.simuladorglosa.servicos.tabela_precos

import br.com.zgsolucoes.simuladorglosa.dominio.ItemTabela
import br.com.zgsolucoes.simuladorglosa.dominio.TabelaDePrecos
import br.com.zgsolucoes.simuladorglosa.repositorios.TabelaDePrecosRepositorio
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@CompileStatic
class ServicoTabelaDePrecos {

	@Inject
	TabelaDePrecosRepositorio itemTabelaRepositorio

	ItemTabela findByCodigo(String codigo) {
		TabelaDePrecos itemTabela = itemTabelaRepositorio
				.findById(codigo)
				.orElseThrow { new Exception("Item ${codigo} não encontrado") }
		return new ItemTabela().tap {
			it.codigo = itemTabela.codigo
			valor = itemTabela.valor
		}
	}

}
