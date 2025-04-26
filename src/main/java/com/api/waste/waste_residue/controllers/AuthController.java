package com.api.waste.waste_residue.controllers;

import com.api.waste.waste_residue.DTO.JwtResponse;
import com.api.waste.waste_residue.models.Student;
import com.api.waste.waste_residue.repository.StudentRepository;
import com.api.waste.waste_residue.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Para encriptar a senha

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Para gerar o JWT

    @PostMapping("/register")
    public String register(@RequestBody Student student) {
        if (studentRepository.findByRegistration(student.getRegistration()).isPresent()) {
            return "Registration number already exists!";
        }

        // Criptografando a senha
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // Atribuindo uma role padrão, se necessário
        if (student.getRole() == null) {
            student.setRole("STUDENT");
        }

        studentRepository.save(student);
        return "Student registered successfully!";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody Student student) {
        Optional<Student> existingStudent = studentRepository.findByRegistration(student.getRegistration());
        if (existingStudent.isPresent() && passwordEncoder.matches(student.getPassword(), existingStudent.get().getPassword())) {
            // Obter as roles do estudante
            List<String> roles = List.of(existingStudent.get().getRole());

            // Gerando o token JWT com roles
            String token = jwtTokenProvider.generateToken(student.getRegistration().toString(), roles);

            return new JwtResponse(token);  // Retorna o objeto com o token
        } else {
            throw new RuntimeException("Invalid registration or password!");
        }
    }

}
