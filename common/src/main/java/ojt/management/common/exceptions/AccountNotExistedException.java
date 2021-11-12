package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotExistedException extends CrudException {
    public AccountNotExistedException() {
        super("Account dose not existed!", HttpStatus.BAD_REQUEST);
    }
}
