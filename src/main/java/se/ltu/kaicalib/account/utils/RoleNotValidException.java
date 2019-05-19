package se.ltu.kaicalib.account.utils;

/**
 * A role that is not valid raises this exception
 */
public class RoleNotValidException extends AccountsException {
    public RoleNotValidException(String message) {
        super(message + "(Check: is db loaded with roles?)");
    }
}
