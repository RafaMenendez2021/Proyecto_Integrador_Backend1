package dh.backend.clinica.repository;

import dh.backend.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
    @Query("Select p from Paciente p where LOWER(p.apellido) = LOWER(:apellido)")
    List<Paciente> buscarPorApellido(String apellido);

    @Query("Select p from Paciente p where LOWER(p.nombre) = LOWER(:nombre)")
    List<Paciente> findByNombreLike(@Param("nombre") String nombre);
}
