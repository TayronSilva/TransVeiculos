package br.sistemafrota.REPOSITORY;

import br.sistemafrota.JPA.MotoristaJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface MotoristaREPOSITORY extends JpaRepository<MotoristaJPA, Long> {
    Long countByEmpresa_Id(Long empresaId);
    Long countByEmpresa_IdAndDataCadastroLessThanEqual(Long empresaId, LocalDate data);
    List<MotoristaJPA> findByEmpresaId(Long empresaId);
    boolean existsByIdentidade(String identidade);
    boolean existsByHabilitacao(String habilitacao);
    List<MotoristaJPA> findByFrotaIdIsNull();
}
