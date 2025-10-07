package com.idp.revele_ai.domain.gateways;

import com.idp.revele_ai.domain.gatewayModels.BuscarDeputadoPorIdOutput;
import com.idp.revele_ai.domain.gatewayModels.DeputadoOutput;


public interface IDadosAbertosGateway {
    DeputadoOutput listarDeputados(String nome, String[] siglaUF, String[] siglaPartido) throws Exception;
    BuscarDeputadoPorIdOutput buscarDeputadosPorId(Integer id) throws Exception;



}
