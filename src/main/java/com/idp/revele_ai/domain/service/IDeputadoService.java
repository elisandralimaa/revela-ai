package com.idp.revele_ai.domain.service;

import com.idp.revele_ai.domain.models.*;

public interface IDeputadoService {
    DadosOutput<DeputadoOutput> listarDeputados(String nome, String[] siglaUF, String[] siglaPartido, Integer pagina, Integer itens) throws Exception;
    DadosPorIdOutput<DeputadoDadoCompletoOutput> buscarDeputadosPorId(Integer id) throws Exception;

}
