package com.example.SecurityProject.controller;


import com.example.SecurityProject.dto.ResponseCookieDTO;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @GetMapping("/")
    public String testMainPage(){
        return "Home";
    }
    @GetMapping("/session")
    public ResponseCookieDTO hello(@AuthenticationPrincipal User user, HttpSession HttpSession) {
        String username = "empty";
        String session_id = "empty";

        if (user == null){
            System.out.println("user is null");
        } else {
            session_id = HttpSession.getId();
            username = user.getUsername();
        }

        return new ResponseCookieDTO(username, session_id);
    }



}
