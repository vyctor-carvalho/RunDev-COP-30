package com.api.waste.waste_residue.security;

import com.api.waste.waste_residue.models.Student;
import com.api.waste.waste_residue.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String registrationHeader = request.getHeader("Registration");

        if (registrationHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Registration header missing\"}");
            return false;
        }

        Integer registration;
        try {
            registration = Integer.parseInt(registrationHeader);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid registration number format\"}");
            return false;
        }

        Optional<Student> studentOptional = studentRepository.findByRegistration(registration);

        if (studentOptional.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid registration\"}");
            return false;
        }

        // Verificando se a autenticação do usuário possui a role 'ADMIN' ou se ele tem permissão para acessar o recurso
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied\"}");
            return false;
        }

        return true;
    }

}
