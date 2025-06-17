package com.example.controller;

import com.example.dto.response.GetUser;
import com.example.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("profile/{id}")
    public GetUser getProfileById(@PathVariable(name = "id") UUID userId) {
        return adminService.getProfileById(userId);
    }
}