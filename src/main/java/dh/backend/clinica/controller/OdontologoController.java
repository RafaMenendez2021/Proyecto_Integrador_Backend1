package dh.backend.clinica.controller;

import dh.backend.clinica.entity.Odontologo;
import dh.backend.clinica.exception.ResourceNotFoundException;
import dh.backend.clinica.service.impl.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.agregarOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = odontologoService.buscarUnOdontologo(id);
            Odontologo odontologoARetornar = odontologo.get();
            return ResponseEntity.ok(odontologoARetornar);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> modificarOdontologo(@PathVariable Integer id, @RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoService.buscarUnOdontologo(id);
            odontologo.setId(id); // Establecer el ID del odontólogo
            odontologoService.modificarOdontologo(odontologo);
            return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> listaOdontologos =odontologoService.buscarPorApellido(apellido);
        if(listaOdontologos.size()>0){
            return ResponseEntity.ok(listaOdontologos);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}
