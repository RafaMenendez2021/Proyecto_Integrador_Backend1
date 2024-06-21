package dh.backend.clinica.service.impl;

import dh.backend.clinica.Dto.response.PacienteResponseDto;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.repository.IPacienteRepository;
import dh.backend.clinica.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    private IPacienteRepository pacienteRepository;
    private ModelMapper modelMapper;

    public PacienteService(IPacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
    }

    public Paciente registrarPaciente(Paciente paciente) {
        LOGGER.info("Registrando paciente: {}", paciente);
        try {
            return pacienteRepository.save(paciente);
        } catch (Exception e) {
            LOGGER.error("Error al registrar el paciente: {}", e.getMessage());
            throw new RuntimeException("Error al registrar el paciente", e);
        }
    }

    public Optional<Paciente> buscarPorId(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Buscando paciente por ID: {}", id);
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            return pacienteOptional;
        } else {
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }
    }

    public List<Paciente> buscarTodos() {
        LOGGER.info("Buscando todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        LOGGER.info("Actualizando paciente: {}", paciente);
        try {
            pacienteRepository.save(paciente);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar el paciente: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el paciente", e);
        }
    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando paciente por ID: {}", id);
        Optional<Paciente> pacienteOptional = buscarPorId(id);
        if (pacienteOptional.isPresent()) {
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }
    }

    @Override
    public List<Paciente> buscarPorApellido(String apellido) {
        LOGGER.info("Buscando pacientes por apellido: {}", apellido);
        return pacienteRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        LOGGER.info("Buscando pacientes por nombre: {}", nombre);
        return pacienteRepository.findByNombreLike(nombre);
    }

    private PacienteResponseDto mapToResponseDto(Paciente paciente) {
        return modelMapper.map(paciente, PacienteResponseDto.class);
    }
}