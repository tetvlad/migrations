package ru.netology.jdbc.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/secure")
public class SecureController {


    @Secured("ROLE_READ")
    @GetMapping("/read")
    public String readData() {
        return "Данные для роли READ";
    }

    @RolesAllowed("ROLE_WRITE")
    @GetMapping("/write")
    public String writeData() {
        return "Данные для роли WRITE";
    }


    @PreAuthorize("hasAnyRole('WRITE', 'DELETE')")
    @GetMapping("/modify")
    public String modifyData() {
        return "Данные для роли WRITE or DELETE";
    }


    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/user-data")
    public String getUserData(@RequestParam("username") String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Данные для пользователя: " + username + " (аутентификация: " + auth.getName() + ")";
    }
}