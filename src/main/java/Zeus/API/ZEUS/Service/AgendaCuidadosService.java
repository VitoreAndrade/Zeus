package Zeus.API.ZEUS.Service;

import Zeus.API.ZEUS.Dto.DadosCadastroAgendaDTO;
import Zeus.API.ZEUS.Model.AgendaCuidados;
import Zeus.API.ZEUS.Repository.AgendaCuidadosRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AgendaCuidadosService {
    @Autowired
    private AgendaCuidadosRespository agendaDeCuidadosRepository;

    // 1. Create: Criar um novo agendamento de cuidado
    public ResponseEntity criarAgendaDeCuidado(DadosCadastroAgendaDTO dadosCadastroPetDTO) {
        agendaDeCuidadosRepository.save(new AgendaCuidados(dadosCadastroPetDTO));
        return ResponseEntity.ok().body(dadosCadastroPetDTO);
    }

    // 2. Read: Buscar todos os agendamentos de cuidados
    public List<AgendaCuidados> buscarTodasAsAgendas() {
        return agendaDeCuidadosRepository.findAll();
    }

    // Buscar por ID
    public Optional<AgendaCuidados> buscarAgendaPorId(Long id) {
        return agendaDeCuidadosRepository.findById(id);
    }

    // 3. Update: Atualizar um agendamento de cuidado existente
    public AgendaCuidados atualizarAgendaDeCuidado(Long id, AgendaCuidados agendaDeCuidadosAtualizados) {
        // Verifica se o agendamento existe
        if (agendaDeCuidadosRepository.existsById(id)) {
            agendaDeCuidadosAtualizados.setId(id);
            return agendaDeCuidadosRepository.save(agendaDeCuidadosAtualizados);
        } else {
            // Lança uma exceção ou retorna algum tipo de resposta, dependendo da lógica de negócio
            throw new RuntimeException("Agenda de Cuidados não encontrada para atualização");
        }
    }

    // 4. Delete: Deletar um agendamento de cuidado por ID
    public void excluirAgendaDeCuidado(Long id) {
        if (agendaDeCuidadosRepository.existsById(id)) {
            agendaDeCuidadosRepository.deleteById(id);
        } else {
            throw new RuntimeException("Agenda de Cuidados não encontrada para exclusão");
        }
    }

    // 5. Cancelar: Método adicional para "cancelar" um agendamento de cuidado
    public AgendaCuidados cancelarAgendaDeCuidado(Long id) {
        Optional<AgendaCuidados> agendaDeCuidadosOptional = agendaDeCuidadosRepository.findById(id);
        if (agendaDeCuidadosOptional.isPresent()) {
            AgendaCuidados agendaDeCuidados = agendaDeCuidadosOptional.get();
            agendaDeCuidados.cancelarCuidado();  // Cancela o cuidado
            return agendaDeCuidadosRepository.save(agendaDeCuidados);  // Salva o agendamento com o status de cancelado
        } else {
            throw new RuntimeException("Agenda de Cuidados não encontrada para cancelamento");
        }
    }
}
