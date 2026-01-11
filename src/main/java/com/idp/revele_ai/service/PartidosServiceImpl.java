package com.idp.revele_ai.service;

import com.idp.revele_ai.domain.api.IDadosAbertosApiClient;
import com.idp.revele_ai.domain.models.DadosOutput;
import com.idp.revele_ai.domain.models.DadosPorIdOutput;
import com.idp.revele_ai.domain.models.PartidoOutput;
import com.idp.revele_ai.domain.service.IPartidoService;
import org.springframework.stereotype.Service;

@Service
public class PartidosServiceImpl implements IPartidoService {

    private final IDadosAbertosApiClient dadosAbertosApi;

    public PartidosServiceImpl(IDadosAbertosApiClient dadosAbertosApi){
        this.dadosAbertosApi = dadosAbertosApi;
    }

    @Override
    public DadosOutput<PartidoOutput> listarPartidos(String [] siglaPartido) throws Exception{
        return dadosAbertosApi.listarPartidos(siglaPartido);
    }

    @Override
    public DadosPorIdOutput<PartidoOutput> buscarPartidoPorId(Integer id) throws Exception {
        return dadosAbertosApi.buscarPartidoPorId(id);
    }
}
