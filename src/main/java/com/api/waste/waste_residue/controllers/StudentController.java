package com.api.waste.waste_residue.controllers;

import com.api.waste.waste_residue.models.Student;
import com.api.waste.waste_residue.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Listar todos os estudantes (acesso permitido para qualquer usuário logado)
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Criar um novo estudante (apenas ADMIN pode criar usuários)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Student createStudent(@RequestBody @Valid Student student) {
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    // Atualizar um estudante existente (apenas ADMIN pode atualizar)
    @PutMapping("/{registration}")
    @PreAuthorize("hasRole('ADMIN')")
    public Student updateStudent(@PathVariable Integer registration, @RequestBody @Valid Student studentDetails) {
        Student student = studentRepository.findById(registration).orElseThrow();
        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        student.setSemester(studentDetails.getSemester());
        student.setShift(studentDetails.getShift());
        student.setClassName(studentDetails.getClassName());
        student.setUnit(studentDetails.getUnit());
        student.setPassword(bCryptPasswordEncoder.encode(studentDetails.getPassword()));
        return studentRepository.save(student);
    }

    // Deletar um estudante (apenas ADMIN pode deletar)
    @DeleteMapping("/{registration}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Integer registration) {
        studentRepository.deleteById(registration);
    }

}
