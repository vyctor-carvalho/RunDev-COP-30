package com.api.waste.waste_residue.controllers;

import com.api.waste.waste_residue.DTO.JwtResponse;
import com.api.waste.waste_residue.DTO.LoginRequest;
import com.api.waste.waste_residue.models.Student;
import com.api.waste.waste_residue.repository.StudentRepository;
import com.api.waste.waste_residue.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
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
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        Optional<Student> existingStudent = studentRepository.findByRegistration(loginRequest.getRegistration());

        if (existingStudent.isEmpty()) {
            throw new RuntimeException("Invalid registration or password!");
        }

        System.out.println("Senha enviada: " + loginRequest.getPassword());
        System.out.println("Senha no banco: " + existingStudent.get().getPassword());

        // A comparação correta de hashes já está sendo feita no matches()
        if (!passwordEncoder.matches(loginRequest.getPassword(), existingStudent.get().getPassword())) {
            List<String> roles = List.of(existingStudent.get().getRole());
            String token = jwtTokenProvider.generateToken(existingStudent.get().getRegistration(), roles);
            return new JwtResponse(token);
        } else {
            throw new RuntimeException("Invalid registration or password!");
        }
    }

}
