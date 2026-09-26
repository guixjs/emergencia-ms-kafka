package com.estudos.ms.emergencia.recepcao.controller;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.service.FichaLoteService;
import com.estudos.ms.emergencia.recepcao.service.NovaFichaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ficha")
public class FichaController {

    private NovaFichaService service;
    private FichaLoteService fichaLoteService;

    public FichaController(NovaFichaService service, FichaLoteService fichaLoteService) {
        this.service = service;
        this.fichaLoteService = fichaLoteService;
    }

    @PostMapping("/nova")
    public ResponseEntity<FichaCriadaDTO> novaFicha(@RequestBody NovaFichaRequestDTO novaFichaRequestDTO) {
        var response = this.service.execute(novaFichaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{qtd}/internacoes")
    public String geraInternacao(@PathVariable("qtd") Integer qtd) {
        fichaLoteService.gerarFichaEmLote(qtd, "internacao");
        return "Internações enviadas";
    }

    @PostMapping("/{qtd}/medicacoes")
    public String geraMedicacao(@PathVariable("qtd") Integer qtd) {
        fichaLoteService.gerarFichaEmLote(qtd, "medicacao");
        return "Medicações enviadas";
    }

}
