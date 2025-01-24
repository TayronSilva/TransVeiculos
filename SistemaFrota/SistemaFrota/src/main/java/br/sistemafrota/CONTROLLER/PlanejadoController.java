package br.sistemafrota.CONTROLLER;

import br.sistemafrota.JPA.PlanejadoJPA;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.SERVICE.PlanejadoSERVICE;
import br.sistemafrota.SERVICE.RealizadoSERVICE;
import br.sistemafrota.SERVICE.MotoristaSERVICE;
import br.sistemafrota.SERVICE.FrotaSERVICE;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlanejadoController {

    @Autowired
    private PlanejadoSERVICE planejadoService;

    @Autowired
    private RealizadoSERVICE realizadoService;

    @Autowired
    private MotoristaSERVICE motoristaService;

    @Autowired
    private FrotaSERVICE frotaService;

    // Método auxiliar para verificar se a empresa está logada
    private EmpresaJPA getEmpresaLogada(HttpSession session) {
        return (EmpresaJPA) session.getAttribute("empresaLogada");
    }

    // Rota para exibir o formulário de cadastro
    @GetMapping("/cadastro/planejado")
    public String planejado(HttpSession session, Model model) {
        EmpresaJPA empresaLogada = getEmpresaLogada(session);
        if (empresaLogada == null) {
            return "redirect:/login";
        }
        model.addAttribute("planejado", new PlanejadoJPA());
        return "TelaPlanejado";
    }

    // Rota para processar o cadastro do frete planejado
    @PostMapping("/cadastro/planejado")
    public String cadastroPlanejado(PlanejadoJPA planejado, HttpSession session, Model model) {
        try {
            EmpresaJPA empresaLogada = getEmpresaLogada(session);
            if (empresaLogada == null) {
                return "redirect:/login";
            }

            planejadoService.cadastrarPlanejado(planejado, empresaLogada.getId());
            return "redirect:/principal";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", "Erro ao cadastrar o frete planejado: " + e.getMessage());
            return "TelaPlanejado";
        }
    }

    // Rota para listar os fretes planejados
    @GetMapping("/planejados")
    public String listarPlanejados(HttpSession session, Model model) {
        EmpresaJPA empresaLogada = getEmpresaLogada(session);
        if (empresaLogada == null) {
            return "redirect:/login";
        }

        model.addAttribute("fretesDisponiveis", planejadoService.listarFretesPorStatus(false));
        model.addAttribute("fretesContratados", planejadoService.listarFretesPorStatus(true));
        return "TelaPlanejado";
    }

    // Rota para gerenciar os fretes (exibir informações de fretes e motoristas)
    @GetMapping("/fretes")
    public String gerenciarFretes(HttpSession session, Model model) {
        EmpresaJPA empresaLogada = getEmpresaLogada(session);
        if (empresaLogada == null) {
            return "redirect:/login";
        }

        model.addAttribute("fretesEmAndamento", realizadoService.listarFretesEmAndamento(empresaLogada.getId()));
        model.addAttribute("fretesFinalizados", realizadoService.listarFretesFinalizados(empresaLogada.getId()));
        model.addAttribute("fretesPlanejados", planejadoService.listarFretesPorStatus(false));
        model.addAttribute("motoristas", motoristaService.listarMotoristasDisponiveis());
        model.addAttribute("frotas", frotaService.listarFrotasDisponiveis());

        return "TelaFretes";
    }
}
