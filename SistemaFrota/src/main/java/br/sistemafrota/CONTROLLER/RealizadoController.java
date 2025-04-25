package br.sistemafrota.CONTROLLER;

import br.sistemafrota.SERVICE.RealizadoSERVICE;
import jakarta.servlet.http.HttpSession;
import br.sistemafrota.SERVICE.PlanejadoSERVICE;
import br.sistemafrota.SERVICE.MotoristaSERVICE;
import br.sistemafrota.SERVICE.FrotaSERVICE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import br.sistemafrota.JPA.EmpresaJPA;
import br.sistemafrota.JPA.PlanejadoJPA;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.List;
import br.sistemafrota.JPA.RealizadoJPA;

@Controller
public class RealizadoController {

    private static final Logger LOGGER = Logger.getLogger(RealizadoController.class.getName());

    @Autowired
    private RealizadoSERVICE realizadoService;
    
    @Autowired
    private PlanejadoSERVICE planejadoService;
    
    @Autowired
    private MotoristaSERVICE motoristaService;
    
    @Autowired
    private FrotaSERVICE frotaService;

    @PostMapping("/fretes/iniciar")
    public String iniciarFrete(Long planejadoId, Long motoristaId, Long frotaId, HttpSession session) {
        try {
            EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
            if (empresaLogada == null) {
                return "redirect:/login";
            }

            realizadoService.iniciarFrete(planejadoId, motoristaId, frotaId, empresaLogada.getId());
            return "redirect:/fretes";
        } catch (Exception e) {
            return "redirect:/fretes?erro=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }

    @GetMapping("/fretes-realizados")
    public String listarFretes(Model model) {
        List<RealizadoJPA> fretes = realizadoService.listarTodos();
        model.addAttribute("fretes", fretes);
        return "TelaFretes";
    }

    @PostMapping("/fretes/{realizadoId}/concluir")
    public String concluirFrete(@PathVariable Long realizadoId, RedirectAttributes redirectAttributes) {
        try {
            LOGGER.info("Tentando concluir frete com ID: " + realizadoId);
            realizadoService.concluirFrete(realizadoId);
            redirectAttributes.addFlashAttribute("mensagem", "Frete concluído com sucesso!");
            return "redirect:/fretes-realizados";
        } catch (Exception e) {
            LOGGER.severe("Erro ao concluir frete: " + e.getMessage());
            redirectAttributes.addFlashAttribute("erro", "Erro ao concluir frete: " + e.getMessage());
            return "redirect:/fretes-realizados";
        }
    }

    @PostMapping("/fretes/cancelar")
    public String cancelarFrete(Long freteId) {
        realizadoService.cancelarFrete(freteId);
        return "redirect:/fretes-realizados";
    }

    @GetMapping("/detalhes/{id}")
    public String detalheFrete(@PathVariable("id") Long freteId, Model model) {
        model.addAttribute("frete", realizadoService.buscarRealizadoPorId(freteId));
        return "TelaDetalheFrete";
    }

    @GetMapping("/motorista/{id}")
    public String listarFretesPorMotorista(@PathVariable Long id, Model model) {
        model.addAttribute("fretes", realizadoService.listarFretesPorMotorista(id));
        return "TelaFretesMotorista";
    }

    @GetMapping("/frota/{id}")
    public String listarFretesPorFrota(@PathVariable Long id, Model model) {
        model.addAttribute("fretes", realizadoService.listarFretesPorFrota(id));
        return "TelaFretesFrota";
    }

    @GetMapping("/iniciar/{id}")
    public String confirmarInicioFrete(@PathVariable("id") Long planejadoId, Model model) {
        try {
            PlanejadoJPA planejado = planejadoService.buscarPlanejadoPorId(planejadoId);
            model.addAttribute("planejado", planejado);
            return "TelaConfirmarInicioFrete";
        } catch (Exception e) {
            return "redirect:/fretes-realizados?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }
} 