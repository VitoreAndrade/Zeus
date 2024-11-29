package Zeus.API.ZEUS.Dto;

import Zeus.API.ZEUS.Model.Racao;
import Zeus.API.ZEUS.Model.Usuario;

import java.time.LocalDateTime;

public record DadosCadastroAgendaDTO(
        String tipoCuidado, String dataHora, String pet, String tutor
) {
}
