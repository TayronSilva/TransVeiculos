package br.sistemafrota.SERVICE;

import br.sistemafrota.JPA.MotoristaJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.REPOSITORY.MotoristaREPOSITORY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class MotoristaSERVICE {

    private static final Logger LOGGER = Logger.getLogger(MotoristaSERVICE.class.getName());

    @Autowired
    private MotoristaREPOSITORY motoristaREPOSITORY;

    @Autowired
    private EmpresaSERVICE empresaService;

    public List<MotoristaJPA> listarTodosMotoristas() {
        LOGGER.info("Iniciando busca de motoristas.");

        List<MotoristaJPA> motoristas = motoristaREPOSITORY.findAll();

        if (motoristas.isEmpty()) {
            LOGGER.warning("Nenhum motorista encontrado.");
            throw new IllegalArgumentException("Nenhum motorista encontrado.");
        }

        LOGGER.info("Encontrados " + motoristas.size() + " motoristas.");
        return motoristas;
    }

    public List<MotoristaJPA> listarMotoristasEmpresa(Long empresaId) {
        return motoristaREPOSITORY.findByEmpresaId(empresaId);
    }

    public void salvarMotorista(MotoristaJPA motorista, Long empresaId) {
        LOGGER.info("Iniciando cadastro do motorista: " + motorista.getNome());

        validarCamposMotorista(motorista);

        if (motoristaREPOSITORY.existsByIdentidade(motorista.getIdentidade())) {
            throw new IllegalArgumentException("Já existe um motorista cadastrado com esta identidade.");
        }
        if (motoristaREPOSITORY.existsByHabilitacao(motorista.getHabilitacao())) {
            throw new IllegalArgumentException("Já existe um motorista cadastrado com esta habilitação.");
        }

        try {
            EmpresaJPA empresa = empresaService.buscarEmpresaPorId(empresaId);
            motorista.setEmpresa(empresa);
            
            motoristaREPOSITORY.save(motorista);
            LOGGER.info("Motorista " + motorista.getNome() + " cadastrado com sucesso!");
        } catch (Exception e) {
            LOGGER.severe("Erro ao salvar o motorista: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar o motorista: " + e.getMessage());
        }
    }

    private void validarCamposMotorista(MotoristaJPA motorista) {
        if (motorista.getNome() == null || !motorista.getNome().matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) {
            LOGGER.severe("Erro: O campo 'Nome' é inválido.");
            throw new IllegalArgumentException("O campo 'Nome' deve conter somente letras.");
        }

        if (motorista.getEndereco() == null || !motorista.getEndereco().matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9\\s,.-]+$")) {
            LOGGER.severe("Erro: O campo 'Endereço' é inválido.");
            throw new IllegalArgumentException("O campo 'Endereço' deve ser válido.");
        }

        if (motorista.getTelefone() == null || !motorista.getTelefone().matches("^[0-9]+$")) {
            LOGGER.severe("Erro: O campo 'Telefone' é inválido.");
            throw new IllegalArgumentException("O campo 'Telefone' deve conter somente números.");
        }

        if (motorista.getIdentidade() == null || !motorista.getIdentidade().matches("^[0-9]+$")) {
            LOGGER.severe("Erro: O campo 'Identidade' é inválido.");
            throw new IllegalArgumentException("O campo 'Identidade' deve conter somente números.");
        }

        if (motorista.getHabilitacao() == null || !motorista.getHabilitacao().matches("^[0-9]+$")) {
            LOGGER.severe("Erro: O campo 'Habilitação' é inválido.");
            throw new IllegalArgumentException("O campo 'Habilitação' deve conter somente números.");
        }

        if (motorista.getPrecohora() == null || motorista.getPrecohora() <= 0) {
            LOGGER.severe("Erro: O campo 'Preço por Hora' é inválido.");
            throw new IllegalArgumentException("O campo 'Preço por Hora' deve ser um valor positivo.");
        }
    }

    public MotoristaJPA buscarMotoristaPorId(Long motoristaId) {
        LOGGER.info("Buscando motorista com ID: " + motoristaId);
        
        return motoristaREPOSITORY.findById(motoristaId)
            .orElseThrow(() -> {
                LOGGER.severe("Motorista não encontrado com ID: " + motoristaId);
                return new RuntimeException("Motorista não encontrado com ID: " + motoristaId);
            });
    }

    public List<MotoristaJPA> listarMotoristasDisponiveis() {
        LOGGER.info("Buscando motoristas disponíveis");
        return motoristaREPOSITORY.findByFrotaIdIsNull();
    }
}
