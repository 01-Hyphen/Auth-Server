package com.auth.controller;

import com.auth.dto.UserObjDto;
import com.auth.entity.UserObj;
import com.auth.service.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    AuthServiceImpl authService;

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session, @RequestParam(required = false) String error) {
        if (error != null) {
            String errorMsg = (String) session.getAttribute("errorMessage");
            model.addAttribute("loginError", errorMsg);
            session.removeAttribute("errorMessage");
        }
        return "login"; // same template
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // Spring looks for templates/login_backup.html
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserObjDto());
        return "signup";
    }
    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("user") UserObjDto userDto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        if (result.hasErrors()) {

            return "signup";
        }
      UserObj user =  authService.createUser(userDto);
        Map<String, String[]> params =
                (Map<String, String[]>) session.getAttribute("AUTH_PARAMS");

        if (params == null) {
            return "redirect:/login";
        }
        //  below is making signed up user authenticated.
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        user.getRoles().stream().map(r-> new SimpleGrantedAuthority(r.getRoleName())).toList());

        SecurityContextHolder.getContext().setAuthentication(auth);

        System.out.println(Arrays.toString(params.get("client_id")));
        // forming authorize request to redirect.
        String query = params.entrySet().stream()
                .flatMap(e -> Arrays.stream(e.getValue())
                        .map(v -> e.getKey() + "=" + URLEncoder.encode(v, StandardCharsets.UTF_8)))
                .collect(Collectors.joining("&"));
        System.out.println(query);

        return "redirect:/oauth2/authorize?" + query;
    }
}


