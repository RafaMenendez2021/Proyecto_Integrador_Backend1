package dh.backend.clinica.service;

import dh.backend.clinica.entity.Domicilio;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PacienteServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);

    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        LOGGER.info("Iniciando setup para pruebas de PacienteService...");
        paciente = new Paciente();
        paciente.setNombre("José");
        paciente.setApellido("Chocoflán");
        paciente.setDni("23001482");
        paciente.setFechaIngreso(LocalDate.of(2021, 4, 28));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Rio Papagayo");
        domicilio.setNumero(5547);
        domicilio.setLocalidad("Puebla");
        domicilio.setProvincia("Puebla");
        paciente.setDomicilio(domicilio);

        // Agregar un nuevo paciente antes de cada test
        paciente = pacienteService.registrarPaciente(paciente);
    }

    @Test
    @DisplayName("Testear que un paciente se guardó correctamente")
    void testPacienteGuardado() {
        LOGGER.info("Test - Guardar Paciente");

        assertNotNull(paciente);
        LOGGER.info("Paciente guardado correctamente: ID {}", paciente.getId());
    }

    @Test
    @DisplayName("Testear búsqueda de un paciente por id")
    void testPacientePorId() throws ResourceNotFoundException {
        LOGGER.info("Test - Buscar Paciente por ID");

        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(paciente.getId());
        assertTrue(pacienteEncontrado.isPresent(), "Paciente no encontrado");
        Paciente paciente1 = pacienteEncontrado.get();
        assertEquals(paciente.getId(), paciente1.getId());
        //LOGGER.info("Paciente encontrado: {}", paciente1);
    }

    @Test
    @DisplayName("Testear búsqueda de todos los pacientes")
    void testBuscarTodos() {
        LOGGER.info("Test - Buscar Todos los Pacientes");

        List<Paciente> pacientes = pacienteService.buscarTodos();
        assertThat(pacientes).isNotEmpty();
        LOGGER.info("Número de pacientes encontrados: {}", pacientes.size());
    }

    @Test
    @DisplayName("Testear actualizar un paciente")
    void testActualizarPaciente() throws ResourceNotFoundException {
        LOGGER.info("Test - Actualizar Paciente");

        assertNotNull(paciente);
        LOGGER.info("Paciente guardado con ID: {}", paciente.getId());

        paciente.setNombre("Juan Pedro");
        pacienteService.actualizarPaciente(paciente);

        Optional<Paciente> pacienteActualizado = pacienteService.buscarPorId(paciente.getId());
        assertTrue(pacienteActualizado.isPresent());
        assertEquals("Juan Pedro", pacienteActualizado.get().getNombre());
        LOGGER.info("Paciente actualizado: {}", pacienteActualizado.get().getNombre());
    }

    @Test
    @DisplayName("Testear eliminación de un paciente existente")
    void testEliminarPaciente() throws ResourceNotFoundException {
        LOGGER.info("Test - Eliminar Paciente");

        assertNotNull(paciente);
        LOGGER.info("Paciente guardado con ID: {}", paciente.getId());

        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(paciente.getId()));
        LOGGER.info("Paciente con ID: {} eliminado correctamente", paciente.getId());

        Optional<Paciente> pacienteEliminado = pacienteService.buscarPorId(paciente.getId());
        assertFalse(pacienteEliminado.isPresent(), "El paciente no fue eliminado correctamente");
    }
    @Test
    @DisplayName("Testear búsqueda de pacientes por apellido")
    void testBuscarPacientesPorApellido() {
        LOGGER.info("Test - Buscar Pacientes por Apellido");

        // Realizar la búsqueda por apellido
        String apellidoBuscado = "Chocoflán";
        List<Paciente> pacientesEncontrados = pacienteService.buscarPorApellido(apellidoBuscado);

        // Verificar que se encontraron pacientes y que el apellido sea el esperado
        assertFalse(pacientesEncontrados.isEmpty(), "No se encontraron pacientes con el apellido proporcionado");
        for (Paciente pacienteEncontrado : pacientesEncontrados) {
            assertEquals(apellidoBuscado, pacienteEncontrado.getApellido(), "El apellido no coincide");
        }
        LOGGER.info("Número de pacientes encontrados con apellido {}: {}", apellidoBuscado, pacientesEncontrados.size());
    }

    @Test
    @DisplayName("Testear búsqueda de pacientes por nombre")
    void testBuscarPacientesPorNombre() {
        LOGGER.info("Test - Buscar Pacientes por Nombre");

        // Realizar la búsqueda por nombre
        String nombreBuscado = "José";
        List<Paciente> pacientesEncontrados = pacienteService.buscarPorNombre(nombreBuscado);

        // Verificar que se encontraron pacientes y que el nombre sea el esperado
        assertFalse(pacientesEncontrados.isEmpty(), "No se encontraron pacientes con el nombre proporcionado");
        for (Paciente pacienteEncontrado : pacientesEncontrados) {
            assertEquals(nombreBuscado, pacienteEncontrado.getNombre(), "El nombre no coincide");
        }
        LOGGER.info("Número de pacientes encontrados con nombre {}: {}", nombreBuscado, pacientesEncontrados.size());
    }

}