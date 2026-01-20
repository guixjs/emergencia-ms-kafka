package com.estudos.ms.emergencia.recepcao.service;

import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.enums.Risco;
import com.estudos.ms.emergencia.recepcao.enums.SetorEspecialidade;
import com.estudos.ms.emergencia.recepcao.model.Ficha;
import com.estudos.ms.emergencia.recepcao.model.Paciente;
import com.estudos.ms.emergencia.recepcao.repository.FichaRepository;
import org.springframework.stereotype.Service;

@Service
public class NovaFichaService {

    private FichaRepository repository;

    public NovaFichaService(FichaRepository repository) {
        this.repository = repository;
    }

    //dto da ficha, informada pelo front
    public void execute(NovaFichaRequestDTO novaFichaRequestDTO) {
        var ficha = montarFica(novaFichaRequestDTO);
        this.repository.save(ficha);

        //chamar repositorio e salvar ficha
        //mandar pro kafka
    }

    private Ficha montarFica(NovaFichaRequestDTO novaFichaRequestDTO) {
        var preferencial = verificarPrefencialidade(novaFichaRequestDTO.idadePaciente());
        var setorIndicado = verificarSetor(novaFichaRequestDTO.sintomas(), novaFichaRequestDTO.idadePaciente());
        var risco = verificarRisco(novaFichaRequestDTO.sintomas(), novaFichaRequestDTO.idadePaciente());


        var paciente = new Paciente(novaFichaRequestDTO.nomePaciente(), novaFichaRequestDTO.idadePaciente());
        return new Ficha(paciente, setorIndicado, risco, novaFichaRequestDTO.sintomas(), preferencial);
    }

    private Risco verificarRisco(String sintomas, Integer idade) {
        if (sintomas.equalsIgnoreCase("Dor no peito")) {
            return Risco.ALTO;
        }
        if (sintomas.equalsIgnoreCase("Fratura")) {
            if (idade < 18) {
                return Risco.ALTO;
            } else {
                return Risco.MEDIO;
            }
        } else {
            return Risco.BAIXO;
        }
    }

    private SetorEspecialidade verificarSetor(String sintomas, Integer idade) {
        if (idade < 18) {
            return SetorEspecialidade.PEDIATRIA;
        }
        return switch (sintomas) {
            case "Dor no peito" -> SetorEspecialidade.CARDIOLOGIA;
            case "Fratura" -> SetorEspecialidade.ORTOPEDIA;
            default -> SetorEspecialidade.CLINICO_GERAL;
        };
    }

    private boolean verificarPrefencialidade(Integer idade) {
        return idade <= 18 || idade >= 65;
    }
}
