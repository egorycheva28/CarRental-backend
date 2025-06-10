package com.example;

import com.example.model.Role;
import com.example.model.Roles;
import com.example.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddRoles {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void add() {
        addRoles();
    }

    private void addRoles() {
        if (roleRepository.findByRole(Roles.ADMIN).isEmpty()) {
            Role role = new Role();
            role.setRole(Roles.ADMIN);
            roleRepository.save(role);
        }
        if (roleRepository.findByRole(Roles.CLIENT).isEmpty()) {
            Role role = new Role();
            role.setRole(Roles.CLIENT);
            roleRepository.save(role);
        }
    }
}
