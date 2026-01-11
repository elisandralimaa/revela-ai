package com.idp.revele_ai.domain.service;

import com.idp.revele_ai.domain.models.DadosOutput;
import com.idp.revele_ai.domain.models.DadosPorIdOutput;
import com.idp.revele_ai.domain.models.PartidoOutput;

public interface IPartidoService {
    DadosOutput<PartidoOutput> listarPartidos(String [] siglaPartido) throws Exception;
    DadosPorIdOutput<PartidoOutput> buscarPartidoPorId(Integer id) throws Exception;
}
