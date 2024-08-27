package br.com.zgsolucoes.simuladorglosa.repositorios

import br.com.zgsolucoes.simuladorglosa.dominio.ItemTabela
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface ItemTabelaRepositorio extends CrudRepository<ItemTabela, String> {
}
