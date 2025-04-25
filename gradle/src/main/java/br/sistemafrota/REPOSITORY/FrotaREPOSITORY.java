package br.sistemafrota.REPOSITORY;

import br.sistemafrota.JPA.FrotaJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FrotaREPOSITORY extends JpaRepository<FrotaJPA, Long> {
    List<FrotaJPA> findByEmpresa(EmpresaJPA empresa);
    List<FrotaJPA> findByEmpresaId(Long empresaId);
    
    Long countByEmpresa_Id(Long empresaId);
    Long countByEmpresa_IdAndDataCadastroLessThanEqual(Long empresaId, Date dataCadastro);
    Long countByEmpresaIdAndDataCadastroLessThanEqual(Long empresaId, LocalDate dataCadastro);

    @Query("SELECT COUNT(f) FROM FrotaJPA f WHERE f.empresa.id = :empresaId AND f.dataCadastro <= :data")
    Long countFrotasByEmpresaAndDate(@Param("empresaId") Long empresaId, @Param("data") LocalDate data);

    List<FrotaJPA> findByStatus(Boolean status);

    List<FrotaJPA> findByStatusTrue();

}
