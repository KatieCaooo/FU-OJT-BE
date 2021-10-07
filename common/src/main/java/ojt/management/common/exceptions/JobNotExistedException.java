package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class JobNotExistedException extends CrudException{
    public JobNotExistedException() { super("Job not existed!", HttpStatus.BAD_REQUEST); }
}
