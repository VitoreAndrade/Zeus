package Zeus.API.ZEUS.Controller;

import Zeus.API.ZEUS.Dto.DadosCadastroAgendaDTO;
import Zeus.API.ZEUS.Model.AgendaCuidados;
import Zeus.API.ZEUS.Service.AgendaCuidadosService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("agenda")
@CrossOrigin(origins = "http://localhost:8081/")
public class AgendaCuidadosController {
    @Autowired
    private AgendaCuidadosService agendaDeCuidadosService;
    // 1. Create: Criar um novo agendamento de cuidado
    @PostMapping
    @Transactional
    public ResponseEntity criarAgendaDeCuidado(@RequestBody DadosCadastroAgendaDTO agendaDeCuidados) {
        return agendaDeCuidadosService.criarAgendaDeCuidado(agendaDeCuidados);
    }

    // 2. Read: Buscar todos os agendamentos de cuidados
    @GetMapping
    public ResponseEntity<List<AgendaCuidados>> buscarTodasAsAgendas() {
        List<AgendaCuidados> agendas = agendaDeCuidadosService.buscarTodasAsAgendas();
        return new ResponseEntity<>(agendas, HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<AgendaCuidados> buscarAgendaPorId(@PathVariable Long id) {
        Optional<AgendaCuidados> agendaDeCuidados = agendaDeCuidadosService.buscarAgendaPorId(id);
        return agendaDeCuidados.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Update: Atualizar um agendamento de cuidado
    @PutMapping("/{id}")
    public ResponseEntity<AgendaCuidados> atualizarAgendaDeCuidado(@PathVariable Long id,
                                                                     @RequestBody AgendaCuidados agendaDeCuidadosAtualizados) {
        try {
            AgendaCuidados agendaAtualizada = agendaDeCuidadosService.atualizarAgendaDeCuidado(id, agendaDeCuidadosAtualizados);
            return new ResponseEntity<>(agendaAtualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 4. Delete: Deletar um agendamento de cuidado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendaDeCuidado(@PathVariable Long id) {
        try {
            agendaDeCuidadosService.excluirAgendaDeCuidado(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 5. Cancelar um agendamento de cuidado
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendaCuidados> cancelarAgendaDeCuidado(@PathVariable Long id) {
        try {
            AgendaCuidados agendaCancelada = agendaDeCuidadosService.cancelarAgendaDeCuidado(id);
            return new ResponseEntity<>(agendaCancelada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
