package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class NotPermissionException extends CrudException {
    public NotPermissionException() {
        super("You are not permission to use this function!", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
