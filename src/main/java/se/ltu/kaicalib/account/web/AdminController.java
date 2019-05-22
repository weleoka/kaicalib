package se.ltu.kaicalib.account.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Secured("ADMIN")
public class AdminController {

    @GetMapping("/admin/profile")
    public String getProfile(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model
            .addAttribute("uri", request.getRequestURI())
            .addAttribute("user", auth.getName())
            .addAttribute("roles", auth.getAuthorities());

        return "account/admin/profile";
    }

}
