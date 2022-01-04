package br.com.zup.LeadCollector.usuario;

import br.com.zup.LeadCollector.usuario.dtos.CadastrarUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrarUsuario(@RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(cadastrarUsuarioDTO.getEmail());
        usuario.setSenha(cadastrarUsuarioDTO.getSenha());
        return usuarioService.salvarUsuario(usuario);
    }
}
