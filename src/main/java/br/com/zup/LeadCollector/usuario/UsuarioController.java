package br.com.zup.LeadCollector.usuario;

import br.com.zup.LeadCollector.config.security.UsuarioLogado;
import br.com.zup.LeadCollector.usuario.dtos.CadastroUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody CadastroUsuarioDTO cadastrarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(cadastrarUsuarioDTO.getEmail());
        usuario.setSenha(cadastrarUsuarioDTO.getSenha());
        usuarioService.salvarUsuario(usuario);
    }

    @PutMapping
    public void atualizarDadosDoUsuario(@RequestBody CadastroUsuarioDTO cadastrarUsuarioDTO,Authentication authentication) {
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        System.out.println(usuarioLogado.getId());

        Usuario usuario = new Usuario();
        usuario.setEmail(cadastrarUsuarioDTO.getEmail());
        usuario.setSenha(cadastrarUsuarioDTO.getSenha());

        usuarioService.atualizarUsuario(usuario, usuarioLogado.getId());

    }
}
