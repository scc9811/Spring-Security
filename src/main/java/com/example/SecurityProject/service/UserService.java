package com.example.SecurityProject.service;

import com.example.SecurityProject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;
    public String login(String userName, String password){

        return JwtUtil.createJwt(userName, secretKey, expiredMs);




//          아래처럼 secretKey를 하드코딩으로 노출시키면 안되기 때문에 @Value 사용해서 application 에서 설정함.
//        return JwtUtil.createJwt(userName, "~~~~~");

    }
}
