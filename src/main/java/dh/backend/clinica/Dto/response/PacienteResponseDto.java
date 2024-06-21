package dh.backend.clinica.Dto.response;

import dh.backend.clinica.exception.ResourceNotFoundException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacienteResponseDto {
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;

    // Método para validar que el ID del paciente no sea nulo
    public void validarId() throws ResourceNotFoundException {
        if (id == null) {
            throw new ResourceNotFoundException("El ID del paciente es nulo");
        }
    }

    // Método para validar que el nombre del paciente no sea nulo o vacío
    public void validarNombre() throws ResourceNotFoundException {
        if (nombre == null || nombre.isEmpty()) {
            throw new ResourceNotFoundException("El nombre del paciente es nulo o vacío");
        }
    }

    // Método para validar que el apellido del paciente no sea nulo o vacío
    public void validarApellido() throws ResourceNotFoundException {
        if (apellido == null || apellido.isEmpty()) {
            throw new ResourceNotFoundException("El apellido del paciente es nulo o vacío");
        }
    }

    // Método para validar que el DNI del paciente no sea nulo o vacío
    public void validarDni() throws ResourceNotFoundException {
        if (dni == null || dni.isEmpty()) {
            throw new ResourceNotFoundException("El DNI del paciente es nulo o vacío");
        }
    }
}
