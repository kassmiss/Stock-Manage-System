package com.grlife.rela_prog.controller;

import com.grlife.rela_prog.domain.UserInfo;
import com.grlife.rela_prog.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;

    @RequestMapping("/signup")
    public String signup(Model model) {
        return "login/signup";
    }

    @RequestMapping("/save")
    public String save(UserInfo userInfo) {
        loginService.save(userInfo);
        return "redirect:/login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

}
