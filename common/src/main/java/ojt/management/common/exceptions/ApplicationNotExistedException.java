package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationNotExistedException extends CrudException {
    public ApplicationNotExistedException() {
        super("Application not existed exception", HttpStatus.BAD_REQUEST);
    }
}
