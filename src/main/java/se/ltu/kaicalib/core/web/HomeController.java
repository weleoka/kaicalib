package se.ltu.kaicalib.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/welcome", "/home", "/index.*"}, method = RequestMethod.GET)
    public String welcome(Model model, Principal principal) {
        return principal != null ? "redirect:patron/profile" : "index";
    }

    @GetMapping("/error")
    public String getGenericErrorPage() {
        return "error";
    }

    @GetMapping("/test")
    String testAuth(Principal principal, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("warning", "true");
        redirectAttributes.addFlashAttribute("flashAttribute", principal);
        //The advantage of using flashAttributes is that, you can add any object as a flash attribute (as it is stored in session).
        return principal != null ? "redirect:patron/profile" : "redirect:/login";
        //return principal != null ? "account/patron/profile" : "account/public/login";
    }
}
