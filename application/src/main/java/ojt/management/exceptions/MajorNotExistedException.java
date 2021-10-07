package ojt.management.exceptions;

import ojt.management.common.exceptions.CrudException;
import org.springframework.http.HttpStatus;

public class MajorNotExistedException extends CrudException {
    public MajorNotExistedException() {
        super("Major does not exist", HttpStatus.BAD_REQUEST);
    }
}
