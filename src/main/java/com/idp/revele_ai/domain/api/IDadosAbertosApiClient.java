package com.idp.revele_ai.domain.api;

import com.idp.revele_ai.domain.models.BuscarDeputadoPorIdOutput;
import com.idp.revele_ai.domain.models.DeputadoOutput;


public interface IDadosAbertosApiClient {
    DeputadoOutput listarDeputados(
            String nome,
            String[] siglaUF,
            String[] siglaPartido,
            Integer pagina,
            Integer itens
    ) throws Exception;

    BuscarDeputadoPorIdOutput buscarDeputadosPorId(Integer id) throws Exception;
}
