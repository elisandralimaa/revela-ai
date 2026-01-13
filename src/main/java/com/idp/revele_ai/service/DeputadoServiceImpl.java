package com.idp.revele_ai.service;

import com.idp.revele_ai.domain.api.IDadosAbertosApiClient;
import com.idp.revele_ai.domain.models.*;
import com.idp.revele_ai.domain.service.IDeputadoService;
import org.springframework.stereotype.Service;

@Service
public class DeputadoServiceImpl implements IDeputadoService {

    private final IDadosAbertosApiClient dadosAbertosApi;

    public DeputadoServiceImpl(IDadosAbertosApiClient dadosAbertosApi){
        this.dadosAbertosApi = dadosAbertosApi;
    }

    @Override
    public DadosOutput<DeputadoOutput> listarDeputados(String nome, String[] siglaUF, String[] siglaPartido, Integer pagina, Integer itens) throws Exception {
        return dadosAbertosApi.listarDeputados(nome, siglaUF, siglaPartido, pagina, itens);
    }

    @Override
    public DadosPorIdOutput<DeputadoDadoCompletoOutput> buscarDeputadosPorId(Integer id) throws Exception {
        return dadosAbertosApi.buscarDeputadosPorId(id);
    }

}
