package com.idp.revele_ai.resource.controllers;

import com.idp.revele_ai.domain.models.DadosOutput;
import com.idp.revele_ai.domain.models.DadosPorIdOutput;
import com.idp.revele_ai.domain.service.IPartidoService;
import com.idp.revele_ai.service.PartidosServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/partido")
@RestController
@Tag(name = "partidos", description = "rotas para operações relacionada a partidos")

public class PartidoController {
    private final IPartidoService partidoService;

    public PartidoController(IPartidoService partidoService) {
        this.partidoService = partidoService;

    }
        //define o verbo HTTP do endpoint(caminho)
        @GetMapping
        @Operation(description = "Esta rota serve para listar partidos")
        // essa anotação @Operation descreve melhor o que esse endpoint faz.
        public ResponseEntity<DadosOutput> partido(String[] sigla) throws Exception {
            return new ResponseEntity<>(
                    partidoService.listarPartidos(sigla),
                    HttpStatus.OK
            );
        }

        @GetMapping("/{id}")
        @Operation(description = "Esta rota traz informações de partidos por ID")
        public ResponseEntity<DadosPorIdOutput> buscarPartidoPorId(@PathVariable Integer id) throws Exception {
            return new ResponseEntity<>(
                    partidoService.buscarPartidoPorId(id),
                    HttpStatus.OK
            );


        }
    }


