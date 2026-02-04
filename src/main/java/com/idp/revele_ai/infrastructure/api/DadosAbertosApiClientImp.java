package com.idp.revele_ai.infrastructure.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idp.revele_ai.domain.models.*;
import com.idp.revele_ai.domain.api.IDadosAbertosApiClient;
import kong.unirest.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class DadosAbertosApiClientImp implements IDadosAbertosApiClient {

    private static final String BASE_URL = "https://dadosabertos.camara.leg.br/api/v2";
    private final Logger logger = LoggerFactory.getLogger(DadosAbertosApiClientImp.class);

    @Override
    public DadosOutput<DeputadoOutput> listarDeputados(String nome, String[] siglaUf, String[] siglaPartido, Integer pagina, Integer itens) throws Exception {
        try {
            var request = Unirest.get(BASE_URL + "/deputados")
                    .header("Content-Type", "application/json")
                    .queryString("nome", nome);

            if (pagina != null) {
                request.queryString("pagina", pagina);
            }

            if (itens != null) {
                request.queryString("itens", itens);
            }

            if (siglaUf != null) {
                request.queryString("siglaUf", String.join(",", siglaUf));
            }

            if (siglaPartido != null) {
                request.queryString("siglaPartido", String.join(",", siglaPartido));
            }

            var response = request.asString();

            if (response.getStatus() != HttpStatus.OK) {
                logger.error("Status http inválido ao solicitar {}.", response.getBody());
                throw new Exception("Status http inválido ao solicitar.");
            }

            // Deserializar (para manipular os dados em Java é preciso convertê‑la em objetos Java (deserializar))
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(response.getBody(), DadosOutput.class);

        } catch (UnirestException e) {
            // Se houver problemas de rede, DNS, etc. (erro do Unirest)
            logger.atError().setMessage("[DeputadoGateway] - Falha ao solicitar").setCause(e).log();
            throw new Exception("Falha ao solicitar " + ".", e);
        }
    }

    @Override
    public DadosPorIdOutput<DeputadoDadoCompletoOutput> buscarDeputadosPorId(Integer id) throws Exception {

        try {
            GetRequest request = Unirest.get(BASE_URL + "/deputados/{id}")
                    .header("Content-Type", "application/json")
                    //routParam = parâmetro de rota
                    .routeParam("id", String.valueOf(id)); //converte o Integer para String

            //Executa a requisição e obtém a resposta (String JSON)
            HttpResponse<String> response = request.asString();
            if (response.getStatus() != HttpStatus.OK) {
                logger.error("Status http inválido ao solicitar{}", response.getBody());
                throw new Exception("Status http inválido ao solicitar");
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Retorna o objeto mapeado
            return mapper.readValue(response.getBody(), DadosPorIdOutput.class);

        } catch (UnirestException e) {

            logger.atError().setMessage("[DeputadoGateway] - Falha ao solicitar deputado por ID").setCause(e).log();
            throw new Exception("Falha ao solicitar o deputado de iD" + id + ".", e);
        }

    }


    @Override
    public DadosOutput<PartidoOutput> listarPartidos(String [] siglaPartido) throws Exception{
    try{
        var request = Unirest.get(BASE_URL + "/partidos")
                .header("Content-Type", "application/json");

        if (siglaPartido != null && siglaPartido.length > 0){
            request.queryString("siglaPartido",String.join(",", siglaPartido));

        }

        var response = request.asString();

        if (response.getStatus() != HttpStatus.OK) {
            logger.error("Status http inválido ao solicitar {}.", response.getBody());
            throw new Exception("Status http inválido ao solicitar.");
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(response.getBody(), new com.fasterxml.jackson.core.type.TypeReference<DadosOutput<PartidoOutput>>() {});


    }catch (UnirestException e){
        logger.atError().setMessage("[PartidoGateway] - Falha ao solicitar").setCause(e).log();
        throw new Exception("Falha ao solicitar partidos.", e);

    }

    }

    @Override
    public DadosPorIdOutput<PartidoOutput> buscarPartidoPorId(Integer id) throws Exception {
        try {
            GetRequest request = Unirest.get(BASE_URL + "/partidos/{id}")
                    .header("Content-Type", "application/json")
                    .routeParam("id", String.valueOf(id)); //converte o Integer para String

            //Executa a requisição e obtém a resposta (String JSON)
            HttpResponse<String> response = request.asString();
            if (response.getStatus() != HttpStatus.OK) {
                logger.error("Status http inválido ao solicitar{}", response.getBody());
                throw new Exception("Status http inválido ao solicitar");
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Retorna o objeto mapeado
            return mapper.readValue(response.getBody(), DadosPorIdOutput.class);

        } catch (UnirestException e) {

            logger.atError().setMessage("[DeputadoGateway] - Falha ao solicitar deputado por ID").setCause(e).log();
            throw new Exception("Falha ao solicitar o deputado de iD" + id + ".", e);

    }

 }

    // Metodo generico utilziado por rotas diferentes
    private <T> DadosOutput<T> buscarGenerico(String urlSuffix, Integer id, Class<T> tipoRetorno) throws Exception {
        try {
            String url = BASE_URL + "/deputados/" + id + urlSuffix;

            var request = Unirest.get(url).header("Content-Type", "application/json");
            var response = request.asString();

            if (response.getStatus() != HttpStatus.OK) {
                throw new Exception("Erro ao buscar " + urlSuffix);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Necessário por causa do Generics (DadosOutput<T>)
            JavaType type = mapper.getTypeFactory().constructParametricType(DadosOutput.class, tipoRetorno);
            return mapper.readValue(response.getBody(), type);

        } catch (Exception e) {
            logger.error("Erro na chamada genérica", e);
            throw e;
        }
    }

    @Override
    public DadosOutput<DeputadoDespesasOutput> buscarDespesas(Integer id) throws Exception {
        return buscarGenerico("/despesas", id, DeputadoDespesasOutput.class);
    }

    @Override
    public DadosOutput<DeputadoFrentesOutput> buscarFrentes(Integer id) throws Exception {
        return buscarGenerico("/frentes", id, DeputadoFrentesOutput.class);
    }

    @Override
    public DadosOutput<DeputadoHistoricoOutput> buscarHistorico(Integer id) throws Exception {
        return buscarGenerico("/historico", id, DeputadoHistoricoOutput.class);
    }

    @Override
    public DadosOutput<DeputadoMandatosExternosOutput> buscarMandatosExternos(Integer id) throws Exception {
        return buscarGenerico("/mandatosExternos", id, DeputadoMandatosExternosOutput.class);
    }

    @Override
    public DadosOutput<DeputadoOcupacoesOutput> buscarOcupacoes(Integer id) throws Exception {
        return buscarGenerico("/ocupacoes", id, DeputadoOcupacoesOutput.class);
    }

    @Override
    public DadosOutput<DeputadoOrgaosOutput> buscarOrgaos(Integer id) throws Exception {
        return buscarGenerico("/orgaos", id, DeputadoOrgaosOutput.class);
    }
}

