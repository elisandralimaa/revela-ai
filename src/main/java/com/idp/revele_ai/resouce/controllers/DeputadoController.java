package com.idp.revele_ai.resouce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Essa anotação define a rota para o controller
@RequestMapping(value = "/api/deputado")
@RestController
//ele ajuda no nome e descrição da documentação para facilitar a leitura.
@Tag(name = "deputado", description = "rotas para operações relacionada a deputados")
public class DeputadoController {

    //define o verbo HTTP do endpoint(caminho)
    @GetMapping
    // essa anotação @Operation descreve melhor o que esse endpoint faz.
    @Operation(description = "consulta uma lista de deputados")
    public String Deputados(){
        return "Lista de deputdaos: " +
                "\n1. Deputados A" +
                "\n2. Deputado B" +
                "\n3. Deputado C";

    }
}
