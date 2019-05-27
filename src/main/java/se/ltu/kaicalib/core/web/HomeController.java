package se.ltu.kaicalib.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


/**
 * Provides the default base routes for the web application
 */
@Controller
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = {"/", "/welcome", "/home", "/index.*"}, method = RequestMethod.GET)
    public String welcome() {
        return "index";
    }


    @GetMapping("/error")
    public String getGenericErrorPage() {
        return "error";
    }


    @GetMapping("/test") // todo remove later
    String testAuth(Principal principal, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("warning", "true");
        redirectAttributes.addFlashAttribute("flashAttribute", principal);
        //The advantage of using flashAttributes is that, you can add any object as a flash attribute (as it is stored in session).
        return principal != null ? "redirect:patron/profile" : "redirect:/login";
    }


    @GetMapping("/patron/view_reservations")
    public String notImplementedViewReservations(Model model) {
        model.addAttribute("message", "Sorry this function is not implemented yet.");

        return "error";
    }

}
