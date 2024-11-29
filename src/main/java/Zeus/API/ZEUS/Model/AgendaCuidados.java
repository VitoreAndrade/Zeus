package Zeus.API.ZEUS.Model;

import Zeus.API.ZEUS.Dto.DadosCadastroAgendaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "agenda_cuidado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AgendaCuidados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Descrição do compromisso (ex: vacina, consulta, banho, tosa, etc.)
    private String tipoCuidado;

    // Data e hora do compromisso
    private String dataHora;
    private String pet;
    private String tutor;

//    // Relacionamento com a entidade "Racao" (ou Pet, ou Animal)
//    @ManyToOne
//    @JoinColumn(name = "id_pet")  // ID do pet (assumindo que a tabela "racao" está associada ao pet)
//    private Racao pet;

    // Relacionamento com a entidade "Usuario" (tutor do pet)
//    @ManyToOne
//    @JoinColumn(name = "id_tutor")  // ID do tutor (associado à tabela "usuario")
//    private Usuario tutor;

    // Método para marcar o compromisso como concluído ou cancelado, por exemplo
    public void cancelarCuidado() {
        this.dataHora = null;  // Ou algum outro campo de status de cancelamento
    }

    // Construtor com parâmetros para criar uma agenda de cuidados com base nos dados fornecidos
    public AgendaCuidados(DadosCadastroAgendaDTO dadosCadastroPetDTO) {
        this.tipoCuidado = dadosCadastroPetDTO.tipoCuidado();
        this.dataHora = dadosCadastroPetDTO.dataHora();
        this.pet = dadosCadastroPetDTO.pet();
        this.tutor = dadosCadastroPetDTO.tutor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCuidado() {
        return tipoCuidado;
    }

    public void setTipoCuidado(String tipoCuidado) {
        this.tipoCuidado = tipoCuidado;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
}
