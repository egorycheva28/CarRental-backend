package com.example.controller;

import com.example.dto.requests.EditUser;
import com.example.dto.response.GetUser;
import com.example.dto.response.SuccessResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("profile")
    public GetUser getProfile(Authentication authentication) {
        System.out.println(authentication);
        return userService.getProfile(authentication);
    }

    @PatchMapping("activateUser")
    public SuccessResponse activateUser(Authentication authentication) {
        return userService.activateUser(authentication);
    }

    @PatchMapping("deactivateUser")
    public SuccessResponse deactivateUser(Authentication authentication) {
        return userService.deactivateUser(authentication);
    }

    @PatchMapping("edit")
    public SuccessResponse editUser(@RequestBody EditUser data, Authentication authentication) {
        return userService.editUser(data, authentication);
    }
}
