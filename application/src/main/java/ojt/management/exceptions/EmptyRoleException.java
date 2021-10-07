package ojt.management.exceptions;

import ojt.management.common.exceptions.CrudException;
import org.springframework.http.HttpStatus;

public class EmptyRoleException extends CrudException {

    public EmptyRoleException() {
        super("Role can not be empty", HttpStatus.BAD_REQUEST);
    }
}
