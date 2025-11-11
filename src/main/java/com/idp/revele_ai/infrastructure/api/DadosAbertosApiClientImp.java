package com.idp.revele_ai.infrastructure.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idp.revele_ai.domain.models.BuscarDeputadoPorIdOutput;
import com.idp.revele_ai.domain.models.DeputadoOutput;
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
    public DeputadoOutput listarDeputados(String nome, String[] siglaUf, String[] siglaPartido, Integer pagina, Integer itens) throws Exception {
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

            // Desserializar (Tranformando o json em Objeto java)
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(response.getBody(), DeputadoOutput.class);

        } catch (UnirestException e) {
            // Se houver problemas de rede, DNS, etc. (erro do Unirest)
            logger.atError().setMessage("[DeputadoGateway] - Falha ao solicitar").setCause(e).log();
            throw new Exception("Falha ao solicitar " + ".", e);
        }
    }

    @Override
    public BuscarDeputadoPorIdOutput buscarDeputadosPorId(Integer id) throws Exception {

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
            return mapper.readValue(response.getBody(), BuscarDeputadoPorIdOutput.class);

        } catch (UnirestException e) {

            logger.atError().setMessage("[DeputadoGateway] - Falha ao solicitar deputado por ID").setCause(e).log();
            throw new Exception("Falha ao solicitar o deputado de iD" + id + ".", e);
        }

    }


}

