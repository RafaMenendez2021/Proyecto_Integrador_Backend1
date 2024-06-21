package dh.backend.clinica.Dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OdontologoRequestDto {
    private String matricula;
    private String nombre;
    private String apellido;
}

