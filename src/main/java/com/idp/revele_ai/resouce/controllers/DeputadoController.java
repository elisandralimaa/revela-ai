package com.idp.revele_ai.resouce.controllers;

import com.idp.revele_ai.domain.models.BuscarDeputadoPorIdOutput;
import com.idp.revele_ai.domain.models.DeputadoOutput;
import com.idp.revele_ai.domain.api.IDadosAbertosApiClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Essa anotação define a rota para o controller
@RequestMapping(value = "/api/deputado")
@RestController
//ele ajuda no nome e descrição da documentação para facilitar a leitura.
@Tag(name = "deputado", description = "rotas para operações relacionada a deputados")
public class DeputadoController {

    private IDadosAbertosApiClient deputadoGateway;

    public DeputadoController(IDadosAbertosApiClient deputadoGateway){
        this.deputadoGateway = deputadoGateway;
    }

    //define o verbo HTTP do endpoint(caminho)
    @GetMapping
    @Operation(description = "Esta rota serve para listar deputados")
    // essa anotação @Operation descreve melhor o que esse endpoint faz.
    public ResponseEntity<DeputadoOutput> deputados(String nome, String[] siglaUF, String[] siglaPartido) throws Exception {
        return new ResponseEntity<>(
                deputadoGateway.listarDeputados(nome, siglaUF, siglaPartido),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    @Operation(description = "Esta rota traz informações de deputadps por ID")
    public ResponseEntity<BuscarDeputadoPorIdOutput> buscarDeputadoPorId(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(
                deputadoGateway.buscarDeputadosPorId(id),
                HttpStatus.OK
        );
    }
}
