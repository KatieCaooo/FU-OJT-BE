package ojt.management.common.exceptions;

import org.springframework.http.HttpStatus;

public class CompanyNameAlreadyExistedException extends  CrudException{
    public CompanyNameAlreadyExistedException() { super("Company name already existed!", HttpStatus.BAD_REQUEST); }
}
