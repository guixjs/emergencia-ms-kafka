package com.estudos.ms.emergencia.recepcao.service;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.enums.Risco;
import com.estudos.ms.emergencia.recepcao.enums.SetorEspecialidade;
import com.estudos.ms.emergencia.recepcao.mapper.FichaMapper;
import com.estudos.ms.emergencia.recepcao.model.Ficha;
import com.estudos.ms.emergencia.recepcao.model.Paciente;
import com.estudos.ms.emergencia.recepcao.repository.FichaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class NovaFichaService {

    private FichaRepository repository;
    private KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NovaFichaService(FichaRepository repository, KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public FichaCriadaDTO execute(NovaFichaRequestDTO novaFichaRequestDTO) {
        var ficha = montarFica(novaFichaRequestDTO);
        var fichaCriada = FichaMapper.converteDeEntidadeParaRespostaDTO(this.repository.save(ficha));

        enviarFichaKafka(fichaCriada);
        return fichaCriada;
    }

    private void enviarFichaKafka(FichaCriadaDTO fichaCriada) {

        String json = objectMapper.writeValueAsString(fichaCriada);
        if (kafkaTemplate != null) {
            kafkaTemplate.send("FICHA_CRIADA", fichaCriada.id(), json);
        }
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
