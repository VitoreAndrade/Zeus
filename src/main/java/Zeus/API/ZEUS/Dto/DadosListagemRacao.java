package Zeus.API.ZEUS.Dto;

import Zeus.API.ZEUS.Model.Racao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record DadosListagemRacao(
        Long id,
        @NotBlank
        String nome,
        @NotNull
        int kgQuantidade,
        @NotNull
        String historicoMedico,
        @NotNull
        String idade
) {
        public DadosListagemRacao (Racao racao){
                this(   racao.getId(),
                        racao.getNome(),
                        racao.getKg(),
                        racao.getHistoricoMedico(),
                        racao.getIdade());
        }
}
