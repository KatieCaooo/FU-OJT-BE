package ojt.management.common.exceptions;


import org.springframework.http.HttpStatus;

public class SemesterAlreadyExistedException extends CrudException {
    public SemesterAlreadyExistedException() {
        super("Semester name already existed!", HttpStatus.BAD_REQUEST);
    }
}
