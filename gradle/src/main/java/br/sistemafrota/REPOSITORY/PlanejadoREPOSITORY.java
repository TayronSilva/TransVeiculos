package br.sistemafrota.REPOSITORY;

import br.sistemafrota.JPA.PlanejadoJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanejadoREPOSITORY extends JpaRepository<PlanejadoJPA, Long> {
    List<PlanejadoJPA> findByEmpresaId(Long empresaId);
    List<PlanejadoJPA> findByContratado(Boolean contratado);
}
