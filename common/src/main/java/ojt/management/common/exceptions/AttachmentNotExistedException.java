package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class AttachmentNotExistedException extends CrudException{
    public AttachmentNotExistedException() { super("Attachment not existed!", HttpStatus.BAD_REQUEST);}
}
