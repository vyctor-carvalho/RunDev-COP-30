package com.api.waste.waste_residue.security;

import com.api.waste.waste_residue.models.Student;
import com.api.waste.waste_residue.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AdminInterceptor implements HandlerInterceptor {

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

        if (studentOptional.isEmpty() || !"ADMIN".equals(studentOptional.get().getRole())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Access denied: Admins only\"}");
            return false;
        }

        return true;
    }

}
