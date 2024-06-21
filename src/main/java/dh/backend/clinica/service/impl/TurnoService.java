package dh.backend.clinica.service.impl;

import dh.backend.clinica.Dto.request.TurnoRequestDto;
import dh.backend.clinica.Dto.response.OdontologoResponseDto;
import dh.backend.clinica.Dto.response.PacienteResponseDto;
import dh.backend.clinica.Dto.response.TurnoResponseDto;
import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.entity.Paciente;
import dh.backend.clinica.entity.Turno;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.repository.IOdontologoRepository;
import dh.backend.clinica.repository.IPacienteRepository;
import dh.backend.clinica.repository.ITurnoRepository;
import dh.backend.clinica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private IOdontologoRepository odontologoRepository;
    private IPacienteRepository pacienteRepository;
    private ITurnoRepository turnoRepository;
    private ModelMapper modelMapper;

    public TurnoService(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository, ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) {
        try {
            Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
            Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
            Turno turnoARegistrar = new Turno();
            TurnoResponseDto turnoADevolver = null;
            if (paciente.isPresent() && odontologo.isPresent()) {
                turnoARegistrar.setOdontologo(odontologo.get());
                turnoARegistrar.setPaciente(paciente.get());
                turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
                Turno turnoGuardado = turnoRepository.save(turnoARegistrar);
                turnoADevolver = mapToResponseDto(turnoGuardado);
            }
            return turnoADevolver;
        } catch (Exception e) {
            LOGGER.error("Error al registrar el turno: {}", e.getMessage());
            throw new RuntimeException("Error al registrar el turno", e);
        }
    }


    @Override
    public TurnoResponseDto buscarPorId(Integer id) throws ResourceNotFoundException {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if(turnoOptional.isPresent()){
            Turno turnoEncontrado = turnoOptional.get();
            TurnoResponseDto turnoADevolver = mapToResponseDto(turnoEncontrado);
            return turnoADevolver;
        }
        throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar;
        for(Turno turno: turnos){
            turnoAuxiliar = mapToResponseDto(turno);
            turnosADevolver.add(turnoAuxiliar);
        }
        return turnosADevolver;
    }

    @Override
    public void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Optional<Turno> turno = turnoRepository.findById(id);
        if(paciente.isPresent() && odontologo.isPresent() && turno.isPresent()){
            Turno turnoAModificar = turno.get();
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoRepository.save(turnoAModificar);
        }
    }

    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        TurnoResponseDto turnoResponseDto = buscarPorId(id);
        if(turnoResponseDto != null)
            turnoRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnoEntreFechas(startDate, endDate);
        List<TurnoResponseDto> listadoARetornar = new ArrayList<>();
        for (Turno turno: listadoTurnos){
            listadoARetornar.add(mapToResponseDto(turno));
        }
        return listadoARetornar;
    }

    @Override
    public List<Turno> buscarTurnoPorOdontologo(Odontologo odontologo) {
        return turnoRepository.buscarTurnoPorOdontologo(odontologo);
    }

    @Override
    public List<Turno> buscarTurnoPorPaciente(Paciente paciente) {
        return turnoRepository.buscarTurnoPorPaciente(paciente);
    }

    private TurnoResponseDto mapToResponseDto(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return  turnoResponseDto;
    }
}
