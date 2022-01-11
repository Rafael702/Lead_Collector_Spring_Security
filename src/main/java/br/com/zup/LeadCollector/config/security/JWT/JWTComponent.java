package br.com.zup.LeadCollector.config.security.JWT;

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
}
