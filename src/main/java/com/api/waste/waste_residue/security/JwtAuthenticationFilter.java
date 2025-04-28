package com.api.waste.waste_residue.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        System.out.println("Token: " + token);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            String uri = request.getRequestURI();
            if (uri.startsWith("/resisted/units/getunit")) {
                // Se a rota for pública, não faz nada
                filterChain.doFilter(request, response);
                return;
            }
            List<SimpleGrantedAuthority> authorities = jwtTokenProvider.getRolesFromToken(token).stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Adicionando o prefixo "ROLE_" para cada role
                    .collect(Collectors.toList());

            // Criar o objeto de autenticação com as roles
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            // Define a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // Logar a falha na validação de token
            System.out.println("JWT token is invalid or expired.");
        }

        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
