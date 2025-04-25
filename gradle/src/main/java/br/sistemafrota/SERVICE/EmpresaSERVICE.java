package br.sistemafrota.SERVICE;

import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.REPOSITORY.EmpresaREPOSITORY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaSERVICE {

    @Autowired
    private EmpresaREPOSITORY empresaREPOSITORY;

    public EmpresaJPA buscarEmpresaPorId(Long id) {
        return empresaREPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + id));
    }

    public void salvarEmpresa(EmpresaJPA empresa) {
        validarEmpresa(empresa);

        if (empresaREPOSITORY.existsByCnpj(empresa.getCnpj())) {
            throw new IllegalArgumentException("O CNPJ já está cadastrado.");
        }

        try {
            empresaREPOSITORY.save(empresa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar a empresa: " + e.getMessage());
        }
    }

    public EmpresaJPA autenticarEmpresa(String cnpj, String senha) {
        if (cnpj == null || cnpj.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("CNPJ e senha são obrigatórios.");
        }

        return empresaREPOSITORY.findByCnpjAndSenha(cnpj, senha)
                .orElseThrow(() -> new RuntimeException("CNPJ ou senha inválidos."));
    }

    public List<EmpresaJPA> listarTodasEmpresas() {
        return empresaREPOSITORY.findAll();
    }

    public EmpresaJPA buscarEmpresaPorCnpj(String cnpj) {
        return empresaREPOSITORY.findByCnpj(cnpj)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o CNPJ: " + cnpj));
    }

    public void validarEmpresa(EmpresaJPA empresa) {
        if (empresa == null || empresa.getCnpj() == null || empresa.getCnpj().isEmpty() ||
            empresa.getSenha() == null || empresa.getSenha().isEmpty() ||
            empresa.getEmpresa() == null || empresa.getEmpresa().isEmpty() ||
            empresa.getEmail() == null || empresa.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios.");
        }

        if (empresa.getCnpj().length() != 14) {
            throw new IllegalArgumentException("O CNPJ deve ter 14 caracteres.");
        }
    }
}
