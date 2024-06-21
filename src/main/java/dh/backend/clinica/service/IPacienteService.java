package dh.backend.clinica.service;

import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente registrarPaciente(Paciente paciente);
    Optional<Paciente> buscarPorId(Integer id) throws ResourceNotFoundException;
    List<Paciente> buscarTodos();
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Integer id) throws ResourceNotFoundException;
    List<Paciente> buscarPorApellido(String apellido);
    List<Paciente> buscarPorNombre(String nombre);
}
