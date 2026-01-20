package com.estudos.ms.emergencia.recepcao.controller;

import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.service.NovaFichaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ficha")
public class FichaController {


    private NovaFichaService service;

    public FichaController(NovaFichaService service) {
        this.service = service;
    }

    @PostMapping("/nova")
    public void novaFicha(@RequestBody NovaFichaRequestDTO novaFichaRequestDTO){
        this.service.execute(novaFichaRequestDTO);
    }
}
