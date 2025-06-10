package com.example.service;

import com.example.dto.requests.EditUser;
import com.example.dto.response.GetUser;
import com.example.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

public interface UserService {

    GetUser getProfile(Authentication authentication);

    SuccessResponse deleteUser(Authentication authentication);

    SuccessResponse editUser(EditUser data, Authentication authentication);
}
