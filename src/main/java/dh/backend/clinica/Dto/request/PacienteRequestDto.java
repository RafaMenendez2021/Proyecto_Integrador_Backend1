package dh.backend.clinica.Dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacienteRequestDto {
    private String apellido;
    private String nombre;
    private String dni;
}
