package br.com.zup.LeadCollector.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Usuario salvarUsuario(Usuario usuario) {
        String senhaEscondida = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaEscondida);
        return usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario, UUID id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        usuarioOptional.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        Usuario usuarioBanco = usuarioOptional.get();

        if (!usuarioBanco.getEmail().equals(usuario.getEmail())) {
            usuarioBanco.setEmail(usuario.getEmail());
        }

        String senhaEscondida = encoder.encode(usuario.getSenha());
        usuarioBanco.setSenha(senhaEscondida);
        usuarioRepository.save(usuarioBanco);

    }
}
