package se.ltu.kaicalib.account.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.service.UserService;

/**
 * Class validating user objects before persistance operations go ahead.
 *
 * todo Make sure that .trim() and other cleanup is done before validating or try to
 *     clean the input before it gets here and update the user object with that.
 *     Could also be integrated with sanitizing.
 *
 * todo Put the password requirements out to a config file, or implement external password
 *     validator service to give adequate feedback to a user depending on their input. The password
 *     is probably validated on client side JavaScript, although provision still has to be made for
 *     non-JS browsers.
 *
 * todo For DRY: user update make usage of reusable util method and reflection to "merge" objects by field.
 *
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


    /**
     * User validation main
     *
     * These validations have to pass no matter if it's a new user or an
     * old user being updated.
     *
     * This sanitizing and integrity checks
     *
     * @param o
     * @param errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

    }


    /**
     * New user validation
     *
     * Different from updating as certain fields have to be set in the userForm such as password.
     *
     * @param userForm
     * @param errors
     */
    public void validateNew(User userForm, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (userForm.getUsername().length() < 2 || userForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userSignupForm.username");
        }

        if (userService.findUserByUsername(userForm.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userSignupForm.username");
        }

        passwordChecks(userForm, errors);
        validate(userForm, errors);
    }


    /**
     * Updating user validation
     *
     * Compares two objects and updates the fields if they are different.
     *
     * todo implement role consistency checks?
     *
     * @param updateForm
     * @param errors
     */
    public void validateUpdate(User updateForm, Errors errors) {//throws RoleNotValidException {

        if (updateForm.getPassword().length() > 0) {
            passwordChecks(updateForm, errors);
        }

        validate(updateForm, errors);

        //if (!user.getRoles().equals(userForm.getRoles())) { throw new RoleNotValidException("User objects roles do not match."); }
    }


    /**
     * Checks user passwords and that they satisfy
     * constraints.
     *
     * todo Implement more constraints than length.
     *
     * @param form
     * @param errors
     */
    private void passwordChecks(User form, Errors errors) {

        if (form.getPassword().length() < 2 || form.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userSignupForm.password");
        }

        if (!form.getPasswordConfirm().equals(form.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userSignupForm.passwordConfirm");
        }
    }


    /*
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }*/
}
