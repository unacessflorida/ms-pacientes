package cl.unaccess.pacientes.controller;

import cl.unaccess.pacientes.model.Paciente;
import cl.unaccess.pacientes.dto.PacienteDTO;
import cl.unaccess.pacientes.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pacientes")
@Tag(name = "Pacientes", description = "Gestion de pacientes del sistema")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar un nuevo paciente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Paciente creado",
            content = @Content(examples = @ExampleObject(value = "{\"rut\":\"12345678-9\",\"nombre\":\"Juan Perez\",\"telefono\":\"912345678\",\"estadoSalud\":\"Estable\"}"))),
        @ApiResponse(responseCode = "500", description = "El RUT ya existe")
    })
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody PacienteDTO dto) {
        Paciente nuevoPaciente = service.guardarPaciente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @Operation(summary = "Listar todos los pacientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Buscar un paciente por su RUT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Paciente> buscarPorRut(@PathVariable String rut) {
        return service.buscarPorRut(rut)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}