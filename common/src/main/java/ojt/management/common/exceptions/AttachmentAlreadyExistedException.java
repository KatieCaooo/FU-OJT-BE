package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class AttachmentAlreadyExistedException extends CrudException{
    public AttachmentAlreadyExistedException() { super("Attachment already existed", HttpStatus.BAD_REQUEST);}
}
