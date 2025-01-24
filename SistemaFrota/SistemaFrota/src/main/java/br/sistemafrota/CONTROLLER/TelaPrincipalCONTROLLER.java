package br.sistemafrota.CONTROLLER;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import br.sistemafrota.JPA.EmpresaJPA;
import jakarta.servlet.http.HttpSession;

@Controller
public class TelaPrincipalCONTROLLER {
    @GetMapping("/")
    public ModelAndView principal() {
        ModelAndView mv = new ModelAndView("TelaPrincipal");
        return mv;
    }

    @GetMapping("/principal")
    public String exibirTelaPrincipal(HttpSession session, Model model) {
        // Recupera a empresa logada da sessão
        EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
        
        if (empresaLogada != null) {
            // Passa o nome da empresa para o modelo
            model.addAttribute("nomeEmpresa", empresaLogada.getEmpresa());
        } else {
            // Redireciona para o login caso a sessão esteja vazia
            return "redirect:/login";
        }

        return "TelaMenu"; // Nome do arquivo HTML
    }
}
