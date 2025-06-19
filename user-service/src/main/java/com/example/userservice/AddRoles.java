package com.example.userservice;

import com.example.userservice.model.Role;
import com.example.userservice.model.Roles;
import com.example.userservice.repository.RoleRepository;
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
        if (roleRepository.findByRole(Roles.ROLE_ADMIN).isEmpty()) {
            Role role = new Role();
            role.setRole(Roles.ROLE_ADMIN);
            roleRepository.save(role);
        }
        if (roleRepository.findByRole(Roles.ROLE_CLIENT).isEmpty()) {
            Role role = new Role();
            role.setRole(Roles.ROLE_CLIENT);
            roleRepository.save(role);
        }
    }
}
