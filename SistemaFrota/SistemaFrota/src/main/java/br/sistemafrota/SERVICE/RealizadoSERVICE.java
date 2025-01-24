package br.sistemafrota.SERVICE;

import br.sistemafrota.JPA.RealizadoJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.REPOSITORY.RealizadoREPOSITORY;
import br.sistemafrota.REPOSITORY.MotoristaREPOSITORY;
import br.sistemafrota.REPOSITORY.FrotaREPOSITORY;
import br.sistemafrota.REPOSITORY.EmpresaREPOSITORY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import br.sistemafrota.JPA.PlanejadoJPA;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RealizadoSERVICE {
    private static final Logger LOGGER = Logger.getLogger(RealizadoSERVICE.class.getName());

    @Autowired
    private RealizadoREPOSITORY realizadoRepository;

    @Autowired
    private MotoristaREPOSITORY motoristaRepository;

    @Autowired
    private FrotaREPOSITORY frotaRepository;

    @Autowired
    private EmpresaREPOSITORY empresaRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private PlanejadoSERVICE planejadoService;

    @Autowired
    private MotoristaSERVICE motoristaService;

    @Autowired
    private FrotaSERVICE frotaService;

    public RealizadoJPA iniciarFrete(Long planejadoId, Long motoristaId, Long frotaId, Long empresaId) {
        // Buscar e validar o frete planejado
        PlanejadoJPA planejado = planejadoService.buscarPlanejadoPorId(planejadoId);
        if (planejado.getContratado()) {
            throw new RuntimeException("Este frete já foi contratado");
        }

        // Atualizar status do planejado
        planejado.setContratado(true);
        planejadoService.atualizarPlanejado(planejado);

        // Criar novo frete realizado
        RealizadoJPA realizado = new RealizadoJPA();
        realizado.setPlanejado(planejado);
        realizado.setMotorista(motoristaService.buscarMotoristaPorId(motoristaId));
        realizado.setFrota(frotaService.buscarFrotaPorId(frotaId));
        realizado.setRealizado(false);
        realizado.setCancelado(false);
        realizado.setDataInicial(LocalDate.now());
        realizado.setEmpresa(planejado.getEmpresa()); // Importante: associar a empresa

        return realizadoRepository.save(realizado);
    }

    @Transactional
    public RealizadoJPA concluirFrete(Long realizadoId) {
        LOGGER.info("Iniciando conclusão do frete ID: " + realizadoId);
        
        RealizadoJPA realizado = buscarRealizadoPorId(realizadoId);
        if (realizado == null) {
            LOGGER.severe("Frete não encontrado com ID: " + realizadoId);
            throw new RuntimeException("Frete não encontrado");
        }

        if (realizado.isCancelado()) {
            LOGGER.severe("Não é possível concluir um frete cancelado");
            throw new IllegalStateException("Não é possível concluir um frete cancelado");
        }

        if (realizado.isRealizado()) {
            LOGGER.severe("Frete já está concluído");
            throw new IllegalStateException("Este frete já está concluído");
        }

        realizado.setRealizado(true);
        realizado.setDataInicial(LocalDate.now());

        try {
            LOGGER.info("Salvando frete concluído");
            return realizadoRepository.save(realizado);
        } catch (Exception e) {
            LOGGER.severe("Erro ao salvar frete concluído: " + e.getMessage());
            throw new RuntimeException("Erro ao concluir frete: " + e.getMessage());
        }
    }

    public List<RealizadoJPA> listarFretesPorMotorista(Long motoristaId) {
        return realizadoRepository.findByMotoristaId(motoristaId);
    }

    public List<RealizadoJPA> listarFretesPorFrota(Long frotaId) {
        return realizadoRepository.findByFrotaId(frotaId);
    }

    public RealizadoJPA buscarRealizadoPorId(Long id) {
        return realizadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frete não encontrado com ID: " + id));
    }

    public List<RealizadoJPA> listarFretesPorStatus(Boolean realizado) {
        LOGGER.info("Listando fretes por status: " + (realizado ? "concluídos" : "em andamento"));
        
        // Obter a empresa da sessão
        EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
        if (empresaLogada == null) {
            LOGGER.severe("Tentativa de listar fretes sem empresa logada");
            throw new RuntimeException("Empresa não encontrada na sessão");
        }
        
        return realizadoRepository.findByEmpresa_IdAndRealizado(empresaLogada.getId(), realizado);
    }

    public RealizadoJPA cancelarFrete(Long realizadoId) {
        LOGGER.info("Cancelando frete ID: " + realizadoId);
        
        RealizadoJPA realizado = realizadoRepository.findById(realizadoId)
            .orElseThrow(() -> new RuntimeException("Frete não encontrado"));

        if (realizado.isRealizado()) {
            LOGGER.severe("Tentativa de cancelar frete já concluído");
            throw new IllegalStateException("Não é possível cancelar um frete já concluído");
        }

        // Adiciona lógica específica de cancelamento aqui
        realizado.setCancelado(true);
        realizado.setDataCancelamento(LocalDate.now());

        try {
            return realizadoRepository.save(realizado);
        } catch (Exception e) {
            LOGGER.severe("Erro ao cancelar frete: " + e.getMessage());
            throw new RuntimeException("Erro ao cancelar frete: " + e.getMessage());
        }
    }

    public List<RealizadoJPA> listarFretesEmAndamento(Long empresaId) {
        return realizadoRepository.findByEmpresaIdAndRealizadoFalseAndCanceladoFalse(empresaId);
    }

    public List<RealizadoJPA> listarFretesFinalizados(Long empresaId) {
        return realizadoRepository.findByEmpresaIdAndRealizadoTrue(empresaId);
    }

    public List<RealizadoJPA> listarTodos() {
        LOGGER.info("Buscando todos os fretes");
        return realizadoRepository.findAll();
    }
}
