package br.sistemafrota.CONTROLLER;

import br.sistemafrota.JPA.FrotaJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.SERVICE.FrotaSERVICE;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TelaFrotaCONTROLLER {

    @Autowired
    private FrotaSERVICE frotaService;

    @GetMapping("/cadastro/frota")
    public String frota(HttpSession session, Model model) {
        EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
        if (empresaLogada == null) {
            return "redirect:/login";
        }
        return "TelaFrota";
    }

    @PostMapping("/cadastro/frota")
    public String cadastroFrota(
        FrotaJPA frota,
        HttpSession session,
        Model model) {
        try {
            EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
            if (empresaLogada == null) {
                return "redirect:/login";
            }

            FrotaJPA frotaSalva = frotaService.cadastrarFrota(frota, empresaLogada.getId());

            return "redirect:/principal";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "Erro ao cadastrar a frota: " + e.getMessage());
            return "TelaFrota";
        }
    }
}
