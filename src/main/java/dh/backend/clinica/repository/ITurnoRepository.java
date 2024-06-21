package dh.backend.clinica.repository;

import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {
    @Query("Select t from Turno t where t.fecha BETWEEN :startDate and :endDate")
    List<Turno> buscarTurnoEntreFechas(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);
    @Query("SELECT t FROM Turno t WHERE t.odontologo = :odontologo")
    List<Turno> buscarTurnoPorOdontologo(@Param("odontologo")Odontologo odontologo);

    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    List<Turno> buscarTurnoPorPaciente(@Param("paciente")Paciente paciente);


}
