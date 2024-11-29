    package Zeus.API.ZEUS.Service;

    import Zeus.API.ZEUS.Dto.DadosAtualizacaoRacao;
    import Zeus.API.ZEUS.Dto.DadosCadastrosRacao;
    import Zeus.API.ZEUS.Dto.DadosListagemRacao;
    import Zeus.API.ZEUS.Model.Racao;
    import Zeus.API.ZEUS.Model.User;
    import Zeus.API.ZEUS.Model.Usuario;
    import Zeus.API.ZEUS.Repository.RacaoRepository;
    import Zeus.API.ZEUS.Repository.UserRepository;
    import Zeus.API.ZEUS.Repository.UsuarioRepository;
    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import java.math.BigDecimal;
    import java.time.LocalDate;


    @Service
    public class RacaoService {

        @Autowired
        private RacaoRepository repository;
        @Autowired
        private UsuarioRepository usuarioRepository;
        @Autowired
        private TokenService tokenService;
        @Autowired
        private AutenticacaoService autenticacaoService;
        @Autowired
        private UserRepository userRepository;

    //    public ResponseEntity cadastrarRacao(DadosCadastrosRacao dadosCadastros){
    //        Racao racao = new Racao(dadosCadastros);
    //        repository.save(racao);
    //        return ResponseEntity.ok().build();
    //
    //
    //    }

            public ResponseEntity cadastrarPet(DadosCadastrosRacao dadosCadastros, Long idUsuario){
                Usuario usuario = usuarioRepository.findById(idUsuario)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + idUsuario));
                Racao racao = new Racao(dadosCadastros);


                racao.setUsuario(usuario);
                repository.save(racao);
                return ResponseEntity.ok().build();
            }


        public Page<DadosListagemRacao> listarPet(HttpServletRequest request, Pageable lista) {
            String tokenJWT = request.getHeader("Authorization").replace("Bearer ", "");
            String username = tokenService.getSubject(tokenJWT);
            User user = userRepository.findByLogin(username);
            Long usuarioId = user.getId();

            return repository.listarRacaoPorUser(usuarioId, lista).map(DadosListagemRacao::new);
        }

        public ResponseEntity atualizarPet (DadosAtualizacaoRacao dadosAtualizacao){
            Racao racao = repository.findById(dadosAtualizacao.id()).get();

       if(dadosAtualizacao.nome() ==null){
           racao.setNome(racao.getNome());
       }else{
           racao.setNome(dadosAtualizacao.nome());
       }

            switch (dadosAtualizacao.kg()) {
                case 0:
                    racao.setKg(racao.getKg());
                    break;
                default:
                    racao.setKg(dadosAtualizacao.kg());
            }



            if (dadosAtualizacao.historicoMedico() == null) {
                racao.setHistoricoMedico(dadosAtualizacao.historicoMedico());
            }

            if (dadosAtualizacao.idade() == null) {
                racao.setIdade(racao.getIdade());
            } else {
                racao.setIdade(dadosAtualizacao.idade());
            }

            repository.save(racao);
            return ResponseEntity.ok().body(racao);
        }

        public ResponseEntity excluirRacao(Long id){
            Racao racao = repository.getReferenceById(id);
            racao.excluir();
            return ResponseEntity.noContent().build();
        }
    }
