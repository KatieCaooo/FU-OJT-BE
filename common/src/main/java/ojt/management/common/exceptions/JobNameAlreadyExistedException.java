package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class JobNameAlreadyExistedException extends CrudException{
    public JobNameAlreadyExistedException() { super("Job name already existed!", HttpStatus.BAD_REQUEST); }
}
