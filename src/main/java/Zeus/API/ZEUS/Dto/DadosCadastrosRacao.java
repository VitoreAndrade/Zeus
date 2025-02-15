        package Zeus.API.ZEUS.Dto;

        import Zeus.API.ZEUS.Model.User;
        import Zeus.API.ZEUS.Model.Usuario;
        import com.fasterxml.jackson.annotation.JsonFormat;
        import jakarta.validation.constraints.NotBlank;
        import jakarta.validation.constraints.NotNull;

        import java.math.BigDecimal;
        import java.time.LocalDate;
        import java.time.LocalDateTime;
        import java.util.Date;

        public record DadosCadastrosRacao (
                @NotBlank
                String nome,
                @NotNull
                int kg,
                @NotNull
                String historicoMedico,
                String idade

        ){
        }
