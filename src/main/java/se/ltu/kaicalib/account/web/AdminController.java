package se.ltu.kaicalib.account.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ADMIN")
public class AdminController {

    @GetMapping("/admin/profile")
    public String getAdminPage() {
        return "account/admin/profile";
    }
}
