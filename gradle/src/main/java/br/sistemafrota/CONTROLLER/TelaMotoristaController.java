package br.sistemafrota.CONTROLLER;

import br.sistemafrota.JPA.MotoristaJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.SERVICE.MotoristaSERVICE;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TelaMotoristaController
{
    @Autowired
    private MotoristaSERVICE motoristaSERVICE;

    @GetMapping("/cadastro/motorista")
    public String cadastroMotorista(HttpSession session) {
        EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
        if (empresaLogada == null) {
            return "redirect:/login";
        }
        return "TelaCadastroMotorista";
    }

    @PostMapping("/cadastro/motorista")
    @ResponseBody
    public ResponseEntity<?> salvarMotorista(
            @Validated MotoristaJPA motoristaJPA,
            BindingResult bindingResult,
            HttpSession session) {
        
        EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
        if (empresaLogada == null) {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Sessão expirada. Por favor, faça login novamente."
            ));
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Por favor, preencha todos os campos obrigatórios."
            ));
        }

        try {
            motoristaSERVICE.salvarMotorista(motoristaJPA, empresaLogada.getId());
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Motorista cadastrado com sucesso!"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Erro inesperado ao salvar o motorista: " + e.getMessage()
            ));
        }
    }
}