package br.sistemafrota.REPOSITORY;

import br.sistemafrota.JPA.RealizadoJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RealizadoREPOSITORY extends JpaRepository<RealizadoJPA, Long> {
    List<RealizadoJPA> findByMotoristaId(Long motoristaId);
    List<RealizadoJPA> findByFrotaId(Long frotaId);
    List<RealizadoJPA> findByEmpresa_IdAndRealizado(Long empresaId, boolean realizado);
    Long countByEmpresa_IdAndRealizado(Long empresaId, boolean realizado);
    Long countByEmpresa_IdAndDataRealizacaoAndRealizado(Long empresaId, LocalDate data, boolean realizado);
    List<RealizadoJPA> findByRealizadoFalseAndCanceladoFalse();
    
    @Query("SELECT r FROM RealizadoJPA r WHERE r.realizado = false AND r.cancelado = false")
    List<RealizadoJPA> listarFretesEmAndamento();

    List<RealizadoJPA> findByEmpresaIdAndRealizadoFalseAndCanceladoFalse(Long empresaId);
    List<RealizadoJPA> findByEmpresaIdAndRealizadoTrue(Long empresaId);
}
