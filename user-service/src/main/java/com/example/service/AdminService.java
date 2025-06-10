package com.example.service;

import com.example.dto.response.GetUser;

import java.util.UUID;

public interface AdminService {
    GetUser getProfileById(UUID id);
}
