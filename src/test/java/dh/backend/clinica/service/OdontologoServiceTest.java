package dh.backend.clinica.service;

import dh.backend.clinica.Dto.request.OdontologoRequestDto;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OdontologoServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);

    @Autowired
    private OdontologoService odontologoService;
    private ModelMapper modelMapper;

    private OdontologoRequestDto odontologoRequestDto;
    private Odontologo odontologoDB;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        LOGGER.info("Iniciando setup para pruebas de OdontologoService...");
        odontologoRequestDto = new OdontologoRequestDto();
        odontologoRequestDto.setNombre("Robin");
        odontologoRequestDto.setApellido("Zacarias");
        odontologoRequestDto.setMatricula("RZ12345");

        // Agregar un nuevo odontólogo antes de cada test
        odontologoDB = odontologoService.agregarOdontologo(modelMapper.map(odontologoRequestDto, Odontologo.class));
    }

    @Test
    @DisplayName("Testear que un odontólogo se guardó correctamente")
    void testAgregarOdontologo() {
        LOGGER.info("Test - Guardar Odontólogo");

        assertNotNull(odontologoDB);
        LOGGER.info("Odontólogo guardado correctamente: ID {} NOMBRE {}", odontologoDB.getId(), odontologoDB.getNombre());
    }

    @Test
    @DisplayName("Testear búsqueda de un odontólogo por id")
    void testBuscarOdontologoPorId() throws ResourceNotFoundException {
        LOGGER.info("Test - Buscar Odontólogo por ID");
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarUnOdontologo(odontologoDB.getId());
        assertTrue(odontologoEncontrado.isPresent(), "Odontólogo no encontrado");
        Odontologo odontologo1 = odontologoEncontrado.get();
        assertEquals(odontologoDB.getId(), odontologo1.getId());
        LOGGER.info("Odontólogo encontrado: {}", odontologo1.getNombre());
    }

    @Test
    @DisplayName("Testear búsqueda de todos los odontólogos")
    void testBuscarTodosOdontologos() {
        LOGGER.info("Test - Buscar Todos los Odontólogos");
        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();
        assertThat(odontologos).isNotEmpty();
        LOGGER.info("Número de odontólogos encontrados: {}", odontologos.size());
    }

    @Test
    @DisplayName("Testear actualización de un odontólogo")
    void testModificarOdontologo() throws ResourceNotFoundException {
        LOGGER.info("Test - Actualizar Odontólogo");

        assertNotNull(odontologoDB);
        LOGGER.info("Odontólogo guardado con ID: {}", odontologoDB.getId());

        LOGGER.info("Odontologo sin modificar: Nombre: {}, Apellido: {}, Matricula: {}",
                odontologoDB.getNombre(), odontologoDB.getApellido(), odontologoDB.getMatricula());

        odontologoDB.setNombre("Maria");
        odontologoService.modificarOdontologo(odontologoDB);

        Optional<Odontologo> odontologoActualizado = odontologoService.buscarUnOdontologo(odontologoDB.getId());
        assertTrue(odontologoActualizado.isPresent());
        assertEquals("Maria", odontologoActualizado.get().getNombre());
        LOGGER.info("Odontólogo actualizado: Nombre: {}, Apellido: {}, Matricula: {}",
                odontologoActualizado.get().getNombre(), odontologoActualizado.get().getApellido(), odontologoActualizado.get().getMatricula());
    }

    @Test
    @DisplayName("Testear búsqueda de odontólogos por apellido")
    void testBuscarOdontologosPorApellido() {
        LOGGER.info("Test - Buscar Odontólogos por Apellido");

        // Realizar la búsqueda por apellido
        String apellidoBuscado = "Zacarias";
        List<Odontologo> odontologosEncontrados = odontologoService.buscarPorApellido(apellidoBuscado);

        // Verificar que se encontraron odontólogos y que el apellido sea el esperado
        assertFalse(odontologosEncontrados.isEmpty(), "No se encontraron odontólogos con el apellido proporcionado");
        for (Odontologo odontologoEncontrado : odontologosEncontrados) {
            assertEquals(apellidoBuscado, odontologoEncontrado.getApellido(), "El apellido no coincide");
        }
        LOGGER.info("Número de odontólogos encontrados con apellido {}: {}", apellidoBuscado, odontologosEncontrados.size());
    }

    @Test
    @DisplayName("Testear búsqueda de odontólogos por nombre")
    void testBuscarOdontologosPorNombre() {
        LOGGER.info("Test - Buscar Odontólogos por Nombre");

        // Realizar la búsqueda por nombre
        String nombreBuscado = "Robin";
        List<Odontologo> odontologosEncontrados = odontologoService.buscarPorNombre(nombreBuscado);

        // Verificar que se encontraron odontólogos y que el nombre sea el esperado
        assertFalse(odontologosEncontrados.isEmpty(), "No se encontraron odontólogos con el nombre proporcionado");
        for (Odontologo odontologoEncontrado : odontologosEncontrados) {
            assertEquals(nombreBuscado, odontologoEncontrado.getNombre(), "El nombre no coincide");
        }
        LOGGER.info("Número de odontólogos encontrados con nombre {}: {}", nombreBuscado, odontologosEncontrados.size());
    }
}
