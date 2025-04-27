package com.api.waste.waste_residue.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "Zm9vYmFyYmF6cXV4eGN2Ym56dmNuamJ6eG5iam5jenl2Ym5qdm54Ymp2bm5jenZj";


    // Método para gerar o token
    public String generateToken(int username, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 dia de expiração

        System.out.println(roles);
        System.out.println(username);

        return Jwts.builder()
                .setSubject(String.valueOf(username))
                .claim("roles", roles) // Agora passando a lista de String diretamente
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }


    // Método para extrair o nome de usuário do token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Método para extrair as roles (autoridades) do token
    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        List<String> roles = (List<String>) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("roles");
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Método para validar o token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
