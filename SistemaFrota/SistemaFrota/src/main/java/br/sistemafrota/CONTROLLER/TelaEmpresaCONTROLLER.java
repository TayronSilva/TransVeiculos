package br.sistemafrota.CONTROLLER;

import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.SERVICE.EmpresaSERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TelaEmpresaCONTROLLER {

    @Autowired
    private EmpresaSERVICE empresaSERVICE;

    @GetMapping("/cadastro/empresa")
    public ModelAndView cadastroEmpresa() {
        ModelAndView mv = new ModelAndView("TelaCadastroEmpresa");
        return mv;
    }

    @PostMapping("/cadastro/empresa")
    public String salvarEmpresa(@Validated EmpresaJPA empresaJPA,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Verifique os campos obrigatórios.");
            return "redirect:/cadastro/empresa";
        }
        if (!empresaJPA.getSenha().equals(empresaJPA.getConfirmarSenha())) {
            redirectAttributes.addFlashAttribute("errorMessage", "As senhas não coincidem.");
            return "redirect:/cadastro/empresa";
        }
        try {
            empresaSERVICE.salvarEmpresa(empresaJPA);
            redirectAttributes.addFlashAttribute("mensagem", "Empresa cadastrada com sucesso.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cadastro/empresa";
        }
    }
}
