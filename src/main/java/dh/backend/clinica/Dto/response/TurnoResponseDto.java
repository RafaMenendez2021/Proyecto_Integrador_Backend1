package dh.backend.clinica.Dto.response;

import dh.backend.clinica.exception.ResourceNotFoundException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TurnoResponseDto {
    private Integer id;
    private OdontologoResponseDto odontologo;
    private PacienteResponseDto paciente;
    private String fecha;

    // Método para lanzar una excepción si el ID del turno es nulo
    public void validarId() throws ResourceNotFoundException {
        if (id == null) {
            throw new ResourceNotFoundException("El ID del turno es nulo");
        }
    }

    // Método para lanzar una excepción si el odontólogo asociado al turno es nulo
    public void validarOdontologo() throws ResourceNotFoundException {
        if (odontologo == null) {
            throw new ResourceNotFoundException("El odontólogo asociado al turno es nulo");
        }
    }

    // Método para lanzar una excepción si el paciente asociado al turno es nulo
    public void validarPaciente() throws ResourceNotFoundException {
        if (paciente == null) {
            throw new ResourceNotFoundException("El paciente asociado al turno es nulo");
        }
    }

    // Método para lanzar una excepción si la fecha del turno es nula o vacía
    public void validarFecha() throws ResourceNotFoundException {
        if (fecha == null || fecha.isEmpty()) {
            throw new ResourceNotFoundException("La fecha del turno es nula o vacía");
        }
    }
}
