package dh.backend.clinica.controller;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.service.IOdontologoService;
import dh.backend.clinica.service.IPacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/vista")
public class VistaController {
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;

    public VistaController(IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @GetMapping("/buscarPaciente")
    public String buscarPacientePorId(Model model, @RequestParam Integer id)throws ResourceNotFoundException{
        Optional<Paciente> pacienteOptional = pacienteService.buscarPorId(id);
        Paciente paciente = pacienteOptional.get();
        model.addAttribute("especialidad", "Paciente");
        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());
        return "index";
    }

    @GetMapping("/buscarOdontologo")
    public String buscarOdontologoPorId(Model model, @RequestParam Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoService.buscarUnOdontologo(id);
            Odontologo odontologo = odontologoOptional.get();
            model.addAttribute("especialidad", "odontologo");
            model.addAttribute("nombre", odontologo.getNombre());
            model.addAttribute("apellido", odontologo.getApellido());
            return "index";
    }
}
