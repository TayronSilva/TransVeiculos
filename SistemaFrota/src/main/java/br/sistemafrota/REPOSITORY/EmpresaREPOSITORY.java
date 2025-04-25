package br.sistemafrota.REPOSITORY;

import br.sistemafrota.JPA.EmpresaJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaREPOSITORY extends JpaRepository<EmpresaJPA, Long> {
    boolean existsByCnpj(String cnpj); // Verifica se o CNPJ já existe
    
    Optional<EmpresaJPA> findByCnpj(String cnpj); // Busca empresa pelo CNPJ
    
    Optional<EmpresaJPA> findByCnpjAndSenha(String cnpj, String senha); // Busca empresa pelo CNPJ e senha
    
    Optional<EmpresaJPA> findByEmail(String email); // Busca empresa pelo email
}
