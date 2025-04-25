package br.sistemafrota.CONTROLLER;

import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.SERVICE.EmpresaSERVICE;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TelaLoginCONTROLLER {

    @Autowired
    private EmpresaSERVICE empresaSERVICE;

    // Exibe a página de login
    @GetMapping("/login")
    public String exibirLogin(Model model) {
        return "TelaLoginEmpresa"; // Retorna o arquivo TelaLogin.html
    }

    // Processa o login
    @PostMapping("/login")
    public String processarLogin(@RequestParam("cnpj") String cnpj,
                             @RequestParam("senha") String senha,
                             HttpSession session, Model model) {
    try {
        // Autentica a empresa com base no CNPJ e senha
        EmpresaJPA empresaLogada = empresaSERVICE.autenticarEmpresa(cnpj, senha);
        if (empresaLogada != null) {
            // Armazenar a empresa na sessão
            session.setAttribute("empresaLogada", empresaLogada);
            return "redirect:/principal"; // Redireciona para a página principal
        } else {
            model.addAttribute("errorMessage", "CNPJ ou senha inválidos.");
            return "TelaLoginEmpresa"; // Volta para a página de login com mensagem de erro
        }
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Ocorreu um erro: " + e.getMessage());
        return "TelaLoginEmpresa"; // Retorna à página de login em caso de erro
     }


    }


    // Método para logout
  @ GetMapping("/logout")
    public String logout(HttpSession session) {
    session.invalidate(); // Isso limpa todos os atributos da sessão
    return "redirect:/login"; // Redireciona para a página de login
}

}
