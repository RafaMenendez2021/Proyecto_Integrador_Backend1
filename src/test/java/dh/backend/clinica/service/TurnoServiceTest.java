package dh.backend.clinica.service;

import dh.backend.clinica.Dto.request.TurnoRequestDto;
import dh.backend.clinica.Dto.response.TurnoResponseDto;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.entity.Turno;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.service.impl.OdontologoService;
import dh.backend.clinica.service.impl.PacienteService;
import dh.backend.clinica.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TurnoServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);

    @Autowired
    private TurnoService turnoService;
    private Turno turno;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    private Paciente paciente;
    private Odontologo odontologo;

    @BeforeEach
    void setUp() {
        LOGGER.info("Iniciando setup para pruebas de TurnoService...");

        paciente = new Paciente();
        paciente.setNombre("José");
        paciente.setApellido("Chocoflán");
        paciente.setDni("23001482");
        paciente.setFechaIngreso(LocalDate.of(2021, 4, 28));
        pacienteService.registrarPaciente(paciente);

        odontologo = new Odontologo();
        odontologo.setNombre("Ana");
        odontologo.setApellido("Perez");
        odontologo.setMatricula("MP123456");
        odontologoService.agregarOdontologo(odontologo);
    }

    @Test
    @DisplayName("Testear que un turno se registra correctamente")
    void testRegistrarTurno() {
        LOGGER.info("Test - Registrar Turno");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        TurnoResponseDto turnoResponseDto = turnoService.registrar(turnoRequestDto);
        assertNotNull(turnoResponseDto);
        LOGGER.info("Turno registrado correctamente: ID {}", turnoResponseDto.getId());
    }

    @Test
    @DisplayName("Testear búsqueda de un turno por id")
    void testBuscarTurnoPorId() {
        LOGGER.info("Test - Buscar Turno por ID");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        TurnoResponseDto turnoRegistrado = turnoService.registrar(turnoRequestDto);
        assertNotNull(turnoRegistrado);

        try {
            TurnoResponseDto turnoEncontrado = turnoService.buscarPorId(turnoRegistrado.getId());
            assertNotNull(turnoEncontrado);
            assertEquals(turnoRegistrado.getId(), turnoEncontrado.getId());
            LOGGER.info("Turno encontrado: {}", turnoEncontrado);
        } catch (ResourceNotFoundException e) {
            fail("Se lanzó una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Testear búsqueda de todos los turnos")
    void testBuscarTodosTurnos() {
        LOGGER.info("Test - Buscar Todos los Turnos");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        turnoService.registrar(turnoRequestDto);
        List<TurnoResponseDto> turnos = turnoService.buscarTodos();
        assertFalse(turnos.isEmpty(), "No se encontraron turnos");
        LOGGER.info("Número de turnos encontrados: {}", turnos.size());
    }

    @Test
    @DisplayName("Testear actualización de un turno")
    void testActualizarTurno() {
        LOGGER.info("Test - Actualizar Turno");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        TurnoResponseDto turnoRegistrado = turnoService.registrar(turnoRequestDto);
        assertNotNull(turnoRegistrado);
        LOGGER.info("Turno registrado con ID: {}", turnoRegistrado.getId());

        turnoRequestDto.setFecha("2023-07-01");

        try {
            turnoService.actualizarTurno(turnoRegistrado.getId(), turnoRequestDto);
            TurnoResponseDto turnoActualizado = turnoService.buscarPorId(turnoRegistrado.getId());
            assertNotNull(turnoActualizado);
            assertEquals("2023-07-01", turnoActualizado.getFecha().toString());
            LOGGER.info("Turno actualizado: {}", turnoActualizado);
        } catch (ResourceNotFoundException e) {
            fail("Se lanzó una excepción inesperada al actualizar el turno: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Testear búsqueda de turnos entre fechas")
    void testBuscarTurnoEntreFechas() {
        LOGGER.info("Test - Buscar Turno entre Fechas");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        turnoService.registrar(turnoRequestDto);

        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 30);
        List<TurnoResponseDto> turnos = turnoService.buscarTurnoEntreFechas(startDate, endDate);

        assertFalse(turnos.isEmpty(), "No se encontraron turnos en el rango de fechas");
        LOGGER.info("Número de turnos encontrados entre las fechas {} y {}: {}", startDate, endDate, turnos.size());
    }

    @Test
    @DisplayName("Testear búsqueda de turnos por odontólogo")
    void testBuscarTurnoPorOdontologo() {
        LOGGER.info("Test - Buscar Turno por Odontólogo");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        TurnoResponseDto turnoRegistrado = turnoService.registrar(turnoRequestDto);

        List<Turno> turnos = turnoService.buscarTurnoPorOdontologo(odontologo);

        assertFalse(turnos.isEmpty(), "No se encontraron turnos para el odontólogo");
        assertEquals(turnoRegistrado.getId(), turnos.get(0).getId());
        LOGGER.info("Número de turnos encontrados para el odontólogo {}: {}", odontologo.getId(), turnos.size());
    }

    @Test
    @DisplayName("Testear búsqueda de turnos por paciente")
    void testBuscarTurnoPorPaciente() {
        LOGGER.info("Test - Buscar Turno por Paciente");

        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2023-06-19");

        TurnoResponseDto turnoRegistrado = turnoService.registrar(turnoRequestDto);

        List<Turno> turnos = turnoService.buscarTurnoPorPaciente(paciente);

        assertFalse(turnos.isEmpty(), "No se encontraron turnos para el paciente");
        assertEquals(turnoRegistrado.getId(), turnos.get(0).getId());
        LOGGER.info("Número de turnos encontrados para el paciente {}: {}", paciente.getId(), turnos.size());
    }
}
