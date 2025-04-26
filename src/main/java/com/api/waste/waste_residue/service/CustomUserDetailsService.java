package com.api.waste.waste_residue.service;

import com.api.waste.waste_residue.models.Student;
import com.api.waste.waste_residue.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Integer registration;
        try {
            registration = Integer.parseInt(username); // Porque seu username é o registration (número)
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Registration inválido: " + username);
        }

        Student student = studentRepository.findByRegistration(registration)
                .orElseThrow(() -> new UsernameNotFoundException("Estudante não encontrado"));

        // Aqui você confere a role do estudante e atribui de acordo com a role do banco
        UserBuilder builder = User.withUsername(student.getRegistration().toString())
                .password(student.getPassword());

        // Verificando o papel do estudante e atribuindo o role correspondente
        if ("ADMIN".equals(student.getRole())) {
            builder.roles("ADMIN");
        } else {
            builder.roles("STUDENT");
        }

        return builder.build();
    }
}
