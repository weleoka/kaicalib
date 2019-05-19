package se.ltu.kaicalib.account.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.service.UserServiceImpl;
import se.ltu.kaicalib.account.utils.RoleNotValidException;
import se.ltu.kaicalib.account.validator.UserValidator;

import java.security.Principal;

@Controller
public class PublicController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserValidator userValidator;




    /* ==== LOGIN ==================================================== */
     @GetMapping("/login")
    public String getLoginPage(Principal principal) {

        if (principal != null) { // if logged in already redirect to home

            return "redirect:patron/profile";
        }

            return "account/public/login";
    }


    /* ==== ADMIN LOGIN ==================================================== */
    @GetMapping("/admin/login")
    public String getAdminLoginPage(Principal principal) {

        return "error";
    }



    /* ==== SIGN UP ================================================= */
    @RequestMapping(value= {"/signup"}, method=RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());

        return "account/public/signup";
    }

    // todo load the default user roles from application config
    // todo load strings from messages source and make locale sensitive.
    // todo hide entire registration form when success.
    // todo check implications of new User() and how it affects the view
    @RequestMapping(value= {"/signup"}, method = RequestMethod.POST)
    public String signup(User user, BindingResult bindingResult, Model model) throws RoleNotValidException {
        String defaultRoleStr = "PATRON";
        userValidator.validate(user, bindingResult);
        User userExists = userService.findUserByUsername(user.getUsername());

        if (userExists != null) {
            logger.debug("User already exists in db {}", userExists.getUsername());
            bindingResult.rejectValue("username", "error.user", "Sorry, the username is already taken.");
        }
        if (bindingResult.hasErrors()) {
            return "account/public/signup";

        } else {
            userService.saveUser(user, defaultRoleStr);
            model.addAttribute("msg", "User has been registered successfully!");
            model.addAttribute("user", new User());

            //return "redirect:/login?success";
            return "account/public/signup";
        }
    }




    /* ==== OTHER ================================================= */
    @GetMapping("/access_denied")
    public String getGenericErrorPage() {
        return "account/error/access_denied";
    }
}