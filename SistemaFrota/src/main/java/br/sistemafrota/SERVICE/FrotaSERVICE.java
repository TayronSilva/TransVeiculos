package br.sistemafrota.SERVICE;

import br.sistemafrota.JPA.FrotaJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.REPOSITORY.FrotaREPOSITORY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;
import java.time.LocalDate;

@Service
public class FrotaSERVICE {
    private static final Logger LOGGER = Logger.getLogger(FrotaSERVICE.class.getName());

    @Autowired
    private FrotaREPOSITORY frotaRepository;
 
    @Autowired
    private EmpresaSERVICE empresaService;

    public FrotaJPA cadastrarFrota(FrotaJPA frota, Long empresaId) {
        LOGGER.info("Iniciando cadastro de frota");

        validarFrota(frota);

        // Buscar a empresa associada ao ID
        EmpresaJPA empresa = empresaService.buscarEmpresaPorId(empresaId);
        frota.setEmpresa(empresa);

        // Definir a data de cadastro
        frota.setDataCadastro(LocalDate.now());

        try {
            // Salvar a frota e retornar o objeto salvo
            return frotaRepository.save(frota);
        } catch (Exception e) {
            LOGGER.severe("Erro ao salvar frota: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar frota: " + e.getMessage());
        }
    }

    public List<FrotaJPA> listarFrotasPorEmpresa(Long empresaId) {
        // Recupera as frotas de uma empresa com base no ID da empresa
        return frotaRepository.findByEmpresaId(empresaId);
    }

    public FrotaJPA buscarFrotaPorId(Long id) {
        // Busca a frota pelo ID ou lança exceção caso não exista
        return frotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Frota não encontrada com ID: " + id));
    }

    private void validarFrota(FrotaJPA frota) {
        // Validação dos campos da frota

        if (frota == null) {
            LOGGER.severe("Erro: Dados da frota não podem ser nulos");
            throw new IllegalArgumentException("Dados da frota não podem ser nulos.");
        }

        if (frota.getVeiculo() == null || frota.getVeiculo().trim().isEmpty()) {
            LOGGER.severe("Erro: O tipo de veículo é obrigatório");
            throw new IllegalArgumentException("O tipo de veículo é obrigatório.");
        }

        if (frota.getModelo() == null || frota.getModelo().trim().isEmpty()) {
            LOGGER.severe("Erro: O modelo é obrigatório");
            throw new IllegalArgumentException("O modelo é obrigatório.");
        }

        if (frota.getPlaca() == null || frota.getPlaca().trim().isEmpty()) {
            LOGGER.severe("Erro: A placa é obrigatória");
            throw new IllegalArgumentException("A placa é obrigatória.");
        }

        if (frota.getCor() == null || frota.getCor().trim().isEmpty()) {
            LOGGER.severe("Erro: A cor é obrigatória");
            throw new IllegalArgumentException("A cor é obrigatória.");
        }

        if (frota.getAno() <= 0) {
            LOGGER.severe("Erro: O ano deve ser válido");
            throw new IllegalArgumentException("O ano deve ser válido.");
        }

        if (frota.getIpva() == null || frota.getIpva() < 0) {
            LOGGER.severe("Erro: IPVA deve ser um valor válido");
            throw new IllegalArgumentException("IPVA deve ser um valor válido.");
        }

        if (frota.getSeguro() == null || frota.getSeguro() < 0) {
            LOGGER.severe("Erro: Seguro deve ser um valor válido");
            throw new IllegalArgumentException("Seguro deve ser um valor válido.");
        }

        if (frota.getLicenciamento() == null || frota.getLicenciamento() < 0) {
            LOGGER.severe("Erro: Licenciamento deve ser um valor válido");
            throw new IllegalArgumentException("Licenciamento deve ser um valor válido.");
        }

        if (frota.getObrigatorio() == null || frota.getObrigatorio() < 0) {
            LOGGER.severe("Erro: Seguro obrigatório deve ser um valor válido");
            throw new IllegalArgumentException("Seguro obrigatório deve ser um valor válido.");
        }

        if (frota.getPrecoaluguel() == null || frota.getPrecoaluguel() <= 0) {
            LOGGER.severe("Erro: Preço de aluguel deve ser positivo");
            throw new IllegalArgumentException("Preço de aluguel deve ser positivo.");
        }

        if (frota.getDocumentacao() == null) {
            LOGGER.severe("Erro: A data de documentação é obrigatória");
            throw new IllegalArgumentException("A data de documentação é obrigatória.");
        }

        LocalDate hoje = LocalDate.now();
        if (frota.getDocumentacao().isBefore(hoje)) {
            LOGGER.severe("Erro: A data da documentação não pode ser anterior à data atual");
            throw new IllegalArgumentException("A data da documentação não pode ser anterior à data atual.");
        }
    }

    public List<FrotaJPA> listarFrotasDisponiveis() {
        return frotaRepository.findByStatus(true);
    }
    
}
