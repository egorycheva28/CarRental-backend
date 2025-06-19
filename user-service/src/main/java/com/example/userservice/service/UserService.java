package com.example.userservice.service;

import com.example.userservice.dto.requests.EditUser;
import com.example.userservice.dto.response.GetUser;
import com.example.userservice.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {

    GetUser getProfile(Authentication authentication);

    GetUser getProfileById(UUID id);

    SuccessResponse activateUser(Authentication authentication);

    SuccessResponse deactivateUser(Authentication authentication);

    //SuccessResponse deleteUser(Authentication authentication);

    SuccessResponse editUser(EditUser data, Authentication authentication);
}
