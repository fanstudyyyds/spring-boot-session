package com.fan.controller;

import com.fan.config.JwtConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    Integer port;

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public String set(HttpSession session) {
        String token = JwtConfig.createToken("admin");
        session.setAttribute(token, "admin");
        return String.valueOf(token) + "\t" + port;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(HttpSession session, @RequestParam("token") String token) {
        boolean tokenExpired = JwtConfig.isTokenExpired(JwtConfig.getExpirationDateFromToken(token));
        if (tokenExpired) {
            return "过期" + ":" + port;
        }
        return session.getAttribute(token) + ":" + port;
    }

}
