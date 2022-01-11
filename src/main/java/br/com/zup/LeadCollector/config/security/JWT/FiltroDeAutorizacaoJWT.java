package br.com.zup.LeadCollector.config.security.JWT;

import br.com.zup.LeadCollector.config.security.exception.TokenInvalidoException;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroDeAutorizacaoJWT extends BasicAuthenticationFilter {
    private JWTComponent jwtComponent;
    private UserDetailsService detailsService;

    public FiltroDeAutorizacaoJWT(AuthenticationManager authenticationManager, JWTComponent jwtComponent,
                                  UserDetailsService detailsService) {
        super(authenticationManager);
        this.jwtComponent = jwtComponent;
        this.detailsService = detailsService;
    }

    public UsernamePasswordAuthenticationToken pegarAutenticacao(String token) {
        if (!jwtComponent.tokenValido(token)) {
            throw new TokenInvalidoException();
        }

        Claims claims = jwtComponent.pegarClaims(token);
        UserDetails usuarioLogado = detailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(usuarioLogado, null,
                usuarioLogado.getAuthorities());

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Token ")) {
            try {
                UsernamePasswordAuthenticationToken auth = pegarAutenticacao(token.substring(6));
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (TokenInvalidoException e) {
                response.sendError(HttpStatus.FORBIDDEN.value());
            }
        }
        chain.doFilter(request, response);
    }
}
