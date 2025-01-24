package br.sistemafrota.SERVICE;

import br.sistemafrota.JPA.PlanejadoJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.REPOSITORY.PlanejadoREPOSITORY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PlanejadoSERVICE {
    private static final Logger LOGGER = Logger.getLogger(PlanejadoSERVICE.class.getName());

    @Autowired
    private PlanejadoREPOSITORY planejadoRepository;

    @Autowired
    private EmpresaSERVICE empresaService;

    public PlanejadoJPA cadastrarPlanejado(PlanejadoJPA planejado, Long empresaId) {
        LOGGER.info("Iniciando cadastro de frete planejado");
        
        validarPlanejado(planejado);
        
        EmpresaJPA empresa = empresaService.buscarEmpresaPorId(empresaId);
        planejado.setEmpresa(empresa);
        
        try {
            return planejadoRepository.save(planejado);
        } catch (Exception e) {
            LOGGER.severe("Erro ao salvar frete planejado: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar frete planejado: " + e.getMessage());
        }
    }

    public List<PlanejadoJPA> listarFretesPorEmpresa(Long empresaId) {
        LOGGER.info("Listando fretes para empresa ID: " + empresaId);
        return planejadoRepository.findByEmpresaId(empresaId);
    }

    public List<PlanejadoJPA> listarPlanejadosDisponiveis() {
        return planejadoRepository.findByContratado(false);
    }

    public PlanejadoJPA buscarPlanejadoPorId(Long id) {
        return planejadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Frete planejado não encontrado com ID: " + id));
    }

    public List<PlanejadoJPA> listarFretesPorStatus(boolean contratado) {
        LOGGER.info("Listando fretes com status contratado: " + contratado);
        return planejadoRepository.findByContratado(contratado);
    }

    public PlanejadoJPA atualizarPlanejado(PlanejadoJPA planejado) {
        LOGGER.info("Atualizando frete planejado ID: " + planejado.getId());
        
        if (planejado == null || planejado.getId() == null) {
            throw new IllegalArgumentException("Frete planejado inválido");
        }

        // Verifica se o frete existe
        PlanejadoJPA planejadoExistente = planejadoRepository.findById(planejado.getId())
            .orElseThrow(() -> new RuntimeException("Frete planejado não encontrado"));

        try {
            return planejadoRepository.save(planejado);
        } catch (Exception e) {
            LOGGER.severe("Erro ao atualizar frete planejado: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar frete planejado: " + e.getMessage());
        }
    }

    private void validarPlanejado(PlanejadoJPA planejado) {
        if (planejado == null) {
            throw new IllegalArgumentException("Dados do frete não podem ser nulos");
        }

        if (planejado.getOrigem() == null || planejado.getOrigem().trim().isEmpty()) {
            throw new IllegalArgumentException("Origem é obrigatória");
        }

        if (planejado.getDestino() == null || planejado.getDestino().trim().isEmpty()) {
            throw new IllegalArgumentException("Destino é obrigatório");
        }

        if (planejado.getVolume() == null || planejado.getVolume() <= 0) {
            throw new IllegalArgumentException("Volume deve ser maior que zero");
        }

        if (planejado.getPeso() == null || planejado.getPeso() <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero");
        }

        if (planejado.getInicio() == null || planejado.getInicio().trim().isEmpty()) {
            throw new IllegalArgumentException("Data de início é obrigatória");
        }

        if (planejado.getFim() == null || planejado.getFim().trim().isEmpty()) {
            throw new IllegalArgumentException("Data de fim é obrigatória");
        }

        if (planejado.getPreco() == null || planejado.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
    }
}
