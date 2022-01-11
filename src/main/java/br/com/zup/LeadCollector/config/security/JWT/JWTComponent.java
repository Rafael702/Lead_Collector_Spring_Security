package br.com.zup.LeadCollector.config.security.JWT;

import br.com.zup.LeadCollector.config.security.exception.TokenInvalidoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTComponent {
    private String segredo = "xblau";
    private Long milissegundo = 6000l;

    public String gerarToken(String userName, UUID id) {
        Date vencimento = new Date(System.currentTimeMillis() * milissegundo);
        String token = Jwts.builder().setSubject(userName)
                .claim("IdUsuario", id).setExpiration(vencimento)
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();

        return token;
    }

    public Claims pegarClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(segredo.getBytes())
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception exception) {
            throw new TokenInvalidoException();
        }
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = pegarClaims(token);

            String username = claims.getSubject();
            Date vencimentoToken = claims.getExpiration();
            if (username != null && vencimentoToken != null) {
                return true;
            } else {
                return false;
            }
        } catch (TokenInvalidoException tokenInvalido) {
            return false;
        }
    }
}
