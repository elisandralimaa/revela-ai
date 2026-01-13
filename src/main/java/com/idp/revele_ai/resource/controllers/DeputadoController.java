package com.idp.revele_ai.resource.controllers;

import com.idp.revele_ai.domain.models.DadosPorIdOutput;
import com.idp.revele_ai.domain.models.DadosOutput;
import com.idp.revele_ai.domain.service.IDeputadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Essa anotação define a rota para o controller
@RequestMapping(value = "/api/deputado")
@RestController
//ele ajuda no nome e descrição da documentação para facilitar a leitura.
@Tag(name = "deputado", description = "rotas para operações relacionada a deputados")
public class DeputadoController {

    private final IDeputadoService deputadoService;

    public DeputadoController(IDeputadoService deputadoService){
        this.deputadoService = deputadoService;
    }

    //define o verbo HTTP do endpoint(caminho)
    @GetMapping
    @Operation(description = "Esta rota serve para listar deputados")
    // essa anotação @Operation descreve melhor o que esse endpoint faz.
    public ResponseEntity<DadosOutput> deputados(String nome, String[] siglaUF, String[] siglaPartido, @RequestParam(defaultValue = "1") Integer pagina, @RequestParam(defaultValue = "24") Integer itens) throws Exception {
        return new ResponseEntity<>(
                deputadoService.listarDeputados(nome, siglaUF, siglaPartido, pagina, itens),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    @Operation(description = "Esta rota traz informações de deputadps por ID")
    public ResponseEntity<DadosPorIdOutput> buscarDeputadoPorId(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(
                deputadoService.buscarDeputadosPorId(id),
                HttpStatus.OK
        );
    }
}
