package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class SemesterDisabledException extends CrudException {
    public SemesterDisabledException() {
        super("Semester was disabled!", HttpStatus.BAD_REQUEST);
    }
}
