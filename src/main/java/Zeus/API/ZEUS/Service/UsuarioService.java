package Zeus.API.ZEUS.Service;

import Zeus.API.ZEUS.Dto.DadosAtualizacaoUsuario;
import Zeus.API.ZEUS.Dto.DadosCadastroLogin;
import Zeus.API.ZEUS.Dto.DadosCadastroUsuario;
import Zeus.API.ZEUS.Dto.DadosListagemUsuario;
import Zeus.API.ZEUS.Model.Racao;
import Zeus.API.ZEUS.Model.User;
import Zeus.API.ZEUS.Model.Usuario;
import Zeus.API.ZEUS.Repository.RacaoRepository;
import Zeus.API.ZEUS.Repository.UserRepository;
import Zeus.API.ZEUS.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RacaoRepository racaoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutenticacaoService autenticacaoService;


    public ResponseEntity cadastrarUsuario(DadosCadastroUsuario dadosCadastroUsuario) {
        var usuario = new Usuario(dadosCadastroUsuario);

        var user = userRepository.findByLogin(dadosCadastroUsuario.user().login());
        if (user == null || user.getUsername().isEmpty()) {
            var cadastrar = usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(cadastrar);
        } else {

            throw new RuntimeException("Login já em uso");
        }
    }
    public Page<DadosListagemUsuario> listarUsuario(Pageable lista) {
        return usuarioRepository.findAllByAtivoTrue(lista).map(DadosListagemUsuario::new);
    }


        public ResponseEntity<?> atualizarUsuario(String username, DadosAtualizacaoUsuario dadosAtualizacaoUsuario) {
            Long idUsuario = autenticacaoService.getIdUsuarioPorLogin(username);
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + idUsuario));
            if (dadosAtualizacaoUsuario.nome() != null) {
                usuario.setNome(dadosAtualizacaoUsuario.nome());
            }
            if (dadosAtualizacaoUsuario.email() != null) {
                usuario.setEmail(dadosAtualizacaoUsuario.email());
            }
            if (dadosAtualizacaoUsuario.idade() > 0) {
                usuario.setIdade(dadosAtualizacaoUsuario.idade());
            }
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body(usuario);
        }

    //    public ResponseEntity<?> atualizarUsuario(String username, DadosAtualizacaoUsuario dadosAtualizacaoUsuario) {
//        User user = userRepository.findByLogin(username);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        // Salva o usuário atualizado no banco de dados
//        userRepository.save(user);
//
//        return ResponseEntity.ok().build();
//    }
    public ResponseEntity excluirUsuario(Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.excluir();
        return ResponseEntity.noContent().build();
    }

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public Long getIdUsuarioPorIdLogin(Long idLogin) {
        Usuario usuario = usuarioRepository.findByUsuarioId(idLogin);
        if (usuario != null) {
            return usuario.getId();
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID do login: " + idLogin);
        }
    }
}