package br.sistemafrota.CONTROLLER;

import br.sistemafrota.JPA.EmpresaJPA;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import br.sistemafrota.REPOSITORY.FrotaREPOSITORY;
import br.sistemafrota.REPOSITORY.MotoristaREPOSITORY;
import br.sistemafrota.REPOSITORY.RealizadoREPOSITORY;

@Controller
public class TelaPerfilCONTROLLER {

    @Autowired
    private HttpSession session;

    @Autowired
    private FrotaREPOSITORY frotaRepository;

    @Autowired
    private MotoristaREPOSITORY motoristaRepository;

    @Autowired
    private RealizadoREPOSITORY realizadoRepository;

    @GetMapping("/perfil")
    public ModelAndView getDashboard() {
        ModelAndView mv = new ModelAndView("TelaPerfil");
        
        // Recupera a empresa logada da sessão
        EmpresaJPA empresaLogada = (EmpresaJPA) session.getAttribute("empresaLogada");
        
        if (empresaLogada == null) {
            return new ModelAndView("redirect:/login");
        }

        // Adiciona a empresa e as estatísticas ao modelo
        mv.addObject("empresaLogada", empresaLogada);
        mv.addObject("totalFrotas", frotaRepository.countByEmpresa_Id(empresaLogada.getId()));
        mv.addObject("totalMotoristas", motoristaRepository.countByEmpresa_Id(empresaLogada.getId()));
        mv.addObject("totalEntregas", realizadoRepository.countByEmpresa_IdAndRealizado(empresaLogada.getId(), true));

        return mv;
    }
}
