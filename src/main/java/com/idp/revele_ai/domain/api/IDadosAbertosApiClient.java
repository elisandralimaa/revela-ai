package com.idp.revele_ai.domain.api;

import com.idp.revele_ai.domain.models.*;


public interface IDadosAbertosApiClient {
    DadosOutput<DeputadoOutput> listarDeputados(String nome, String[] siglaUF, String[] siglaPartido, Integer pagina, Integer itens) throws Exception;
    DadosPorIdOutput<DeputadoDadoCompletoOutput> buscarDeputadosPorId(Integer id) throws Exception;
    DadosOutput<PartidoOutput> listarPartidos(String [] siglaPartido) throws Exception;
    DadosPorIdOutput<PartidoOutput> buscarPartidoPorId(Integer id) throws Exception;

}
