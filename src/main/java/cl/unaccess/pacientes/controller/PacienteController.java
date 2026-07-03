package cl.unaccess.pacientes.controller;

import cl.unaccess.pacientes.model.Paciente;
import cl.unaccess.pacientes.dto.PacienteDTO;
import cl.unaccess.pacientes.service.PacienteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody PacienteDTO dto) {
        Paciente nuevoPaciente = service.guardarPaciente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/rut/{rut}")
public ResponseEntity<Paciente> buscarPorRut(@PathVariable String rut) {
    return service.buscarPorRut(rut)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}
}