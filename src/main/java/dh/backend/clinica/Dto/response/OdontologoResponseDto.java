package dh.backend.clinica.Dto.response;

import dh.backend.clinica.exception.ResourceNotFoundException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OdontologoResponseDto {
    private Integer id;
    private String matricula;
    private String nombre;
    private String apellido;

    // Método para validar que el ID del odontólogo no sea nulo
    public void validarId() throws ResourceNotFoundException {
        if (id == null) {
            throw new ResourceNotFoundException("El ID del odontólogo es nulo");
        }
    }

    // Método para validar que la matrícula del odontólogo no sea nula o vacía
    public void validarMatricula() throws ResourceNotFoundException {
        if (matricula == null || matricula.isEmpty()) {
            throw new ResourceNotFoundException("La matrícula del odontólogo es nula o vacía");
        }
    }

    // Método para validar que el nombre del odontólogo no sea nulo o vacío
    public void validarNombre() throws ResourceNotFoundException {
        if (nombre == null || nombre.isEmpty()) {
            throw new ResourceNotFoundException("El nombre del odontólogo es nulo o vacío");
        }
    }

    // Método para validar que el apellido del odontólogo no sea nulo o vacío
    public void validarApellido() throws ResourceNotFoundException {
        if (apellido == null || apellido.isEmpty()) {
            throw new ResourceNotFoundException("El apellido del odontólogo es nulo o vacío");
        }
    }
}
