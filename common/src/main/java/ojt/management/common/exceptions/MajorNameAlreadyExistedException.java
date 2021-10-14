package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class MajorNameAlreadyExistedException extends CrudException {
    public MajorNameAlreadyExistedException() {
        super("Major name already existed!", HttpStatus.BAD_REQUEST);
    }
}
