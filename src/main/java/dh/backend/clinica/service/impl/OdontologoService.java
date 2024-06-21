package dh.backend.clinica.service.impl;

import dh.backend.clinica.Dto.response.OdontologoResponseDto;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.repository.IOdontologoRepository;
import dh.backend.clinica.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);

    private IOdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;

    public OdontologoService(IOdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }
    public Odontologo agregarOdontologo(Odontologo odontologo) {
        LOGGER.info("Agregando odontólogo: {}", odontologo);
        try {
            return odontologoRepository.save(odontologo);
        } catch (Exception e) {
            LOGGER.error("Error al agregar el odontólogo: {}", e.getMessage());
            throw new RuntimeException("Error al agregar el odontólogo", e);
        }
    }


    public Optional<Odontologo> buscarUnOdontologo(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Buscando odontólogo por ID: {}", id);
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if (odontologoOptional.isPresent()) {
            return odontologoOptional;
        } else {
            throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
        }
    }

    public List<Odontologo> buscarTodosOdontologos() {
        LOGGER.info("Buscando todos los odontólogos");
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        LOGGER.info("Modificando odontólogo: {}", odontologo);
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando odontólogo por ID: {}", id);
        Optional<Odontologo> odontologoOptional = buscarUnOdontologo(id);
        if (odontologoOptional.isPresent()) {
            odontologoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
        }
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        LOGGER.info("Buscando odontólogos por apellido: {}", apellido);
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        LOGGER.info("Buscando odontólogos por nombre: {}", nombre);
        return odontologoRepository.findByNombreLike(nombre);
    }

    private OdontologoResponseDto mapToResponseDto(Odontologo odontologo) {
        return modelMapper.map(odontologo, OdontologoResponseDto.class);
    }
}
