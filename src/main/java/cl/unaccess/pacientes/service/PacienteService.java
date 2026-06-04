package cl.unaccess.pacientes.service;

import cl.unaccess.pacientes.model.Paciente;
import cl.unaccess.pacientes.dto.PacienteDTO;
import cl.unaccess.pacientes.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repo;


    public PacienteService(PacienteRepository repo) {
        this.repo = repo;
    }

    public Paciente guardarPaciente(PacienteDTO dto) {
       
        if (repo.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("El paciente con este RUT ya existe");
        }

     
        Paciente p = new Paciente();
        p.setRut(dto.getRut());
        p.setNombre(dto.getNombre());
        p.setTelefono(dto.getTelefono());
        p.setEstadoSalud(dto.getEstadoSalud());

        return repo.save(p);
    }

    public List<Paciente> obtenerTodos() {
        return repo.findAll();
    }
}