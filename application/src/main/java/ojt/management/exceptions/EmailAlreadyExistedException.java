package ojt.management.exceptions;

import ojt.management.common.exceptions.CrudException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistedException extends CrudException {
    public EmailAlreadyExistedException() {
        super("Error: Email is already in use!", HttpStatus.BAD_REQUEST);
    }
}
