package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class AccountDisabledException extends CrudException {
    public AccountDisabledException() {
        super("Account was disabled!", HttpStatus.BAD_REQUEST);
    }
}
