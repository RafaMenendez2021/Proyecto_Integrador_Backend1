package dh.backend.clinica.service;

import dh.backend.clinica.Dto.request.TurnoRequestDto;
import dh.backend.clinica.Dto.response.TurnoResponseDto;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.entity.Turno;
import dh.backend.clinica.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {
    TurnoResponseDto registrar (TurnoRequestDto turnoRequestDto);
    TurnoResponseDto buscarPorId(Integer id)throws ResourceNotFoundException;
    List<TurnoResponseDto> buscarTodos();
    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto);
    void eliminarTurno(Integer id) throws ResourceNotFoundException;

    List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate);
    List<Turno> buscarTurnoPorOdontologo(Odontologo odontologo);
    List<Turno> buscarTurnoPorPaciente(Paciente paciente);
}
