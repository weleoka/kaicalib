package se.ltu.kaicalib.account.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Secured("PATRON")
public class PatronController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserValidator userValidator;


    @GetMapping("/patron/update")
    public String updatePatron(Model model) {
        model.addAttribute(getAuthUser());

        return "account/patron/update";
    }


    @RequestMapping(value= {"/patron/update"}, method = RequestMethod.POST)
    public String updatePatron(User user, BindingResult bindingResult, Model model) {
        User userExists = userService.findUserByUsername(user.getUsername()); // todo not needed but good backup safety check?

        if (userExists == null) {
            logger.error("User trying to be updated does not exist in DB {}", user.getUsername());
            bindingResult.rejectValue("firstname", "error.user", "Sorry, the system has failed. contact and admin and make their day.");
            return "account/public/update";

        } else { // todo implement checks to ensure user is not being upgraded to other role, admin for example.
            userService.updateUser(user);
            model.addAttribute("msg", "User has been updated successfully!");
            model.addAttribute("user", user);

            return "account/public/update";
        }
    }


    @GetMapping("/patron/details")
    public String detailsPatron(Model model) {
        model.addAttribute("authUser", getAuthUser());
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);

        return "account/patron/details";
    }

    @GetMapping("/patron/profile")
    public String getProfile(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model
            .addAttribute("uri", request.getRequestURI())
            .addAttribute("user", auth.getName())
            .addAttribute("roles", auth.getAuthorities());

        return "account/patron/profile";
    }


    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userService.findUserByUsername(username);
    }
}
