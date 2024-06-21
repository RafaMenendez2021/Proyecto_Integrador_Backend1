package dh.backend.clinica.repository;

import dh.backend.clinica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    @Query("select o from Odontologo o where LOWER(o.apellido) = LOWER(:apellido)")
    List<Odontologo> buscarPorApellido(String apellido);

    @Query("Select o from Odontologo o where LOWER(o.nombre) LIKE LOWER(CONCAT('%', :nombre,'%'))")
    List<Odontologo> findByNombreLike(@Param("nombre") String nombre);
}
