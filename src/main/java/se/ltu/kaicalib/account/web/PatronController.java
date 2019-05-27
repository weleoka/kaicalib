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
import org.springframework.web.bind.annotation.SessionAttribute;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.service.UserServiceImpl;
import se.ltu.kaicalib.account.utils.RoleNotValidException;
import se.ltu.kaicalib.account.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;


/**
 * Controller handling the basic actions of what is known as
 * a patron according to the business rules.
 *
 */
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
        model.addAttribute(userService.getAuthUser());

        return "account/patron/update";
    }


    /**
     * Route that takes a user form and applies it
     * to a user already in persistence.
     *
     * Only allows updating of the authenticated user. And not suitable for admin work.
     *
     * The user object has to manually merged as it is a security object and does not
     * grant merge through object reflection.
     *
     * todo Move the user properties update process out from Controller class.
     *
     * The though is to
     *
     * @param userForm
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value= {"/patron/update"}, method = RequestMethod.POST)
    public String updatePatron(User userForm, BindingResult bindingResult, Model model) throws RoleNotValidException {
        User user = userService.getAuthUser();
        userValidator.validateUpdate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("warn", "There has been some errors in the form processing!");

            return "account/patron/update";
        }

        if (userForm.getFirstname() != "") user.setFirstname(userForm.getFirstname());
        if (userForm.getLastname() != "") user.setLastname(userForm.getLastname());
        if (userForm.getAddress() != "") user.setAddress(userForm.getAddress());
        if (userForm.getEmail() != "") user.setEmail(userForm.getEmail());
        if (userForm.getPassword() != "") user.setPassword(userForm.getPassword());

        userService.saveUser(user, "PATRON");
        model.addAttribute("msg", "User has been updated successfully!");
        model.addAttribute("user", user); // todo check what this does to the model. Don't want to return pwd for example.

        return "account/patron/update";
    }


    @GetMapping("/patron/details")
    public String detailsPatron(Model model) {
        model.addAttribute("authUser", userService.getAuthUser());

        return "account/patron/details";
    }


    @GetMapping("/patron/profile")
    public String getProfile(
        HttpServletRequest request,
        Model model,
        @SessionAttribute(name = "sessionStartTime") LocalDateTime startDateTime) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Duration duration = Duration.between(startDateTime, LocalDateTime.now());

        model
            .addAttribute("uri", request.getRequestURI())
            .addAttribute("user", auth.getName())
            .addAttribute("roles", auth.getAuthorities())
            .addAttribute("sessionDuration", duration.getSeconds());

        return "account/patron/profile";
    }
}


/*
        if (userExists == null) {
            logger.error("User trying to be updated does not exist in DB {}", user.getUsername());
            bindingResult.rejectValue("firstname", "error.user", "Sorry, the system has failed. Contact an admin and make their day.");

            return "account/patron/update";

 */
