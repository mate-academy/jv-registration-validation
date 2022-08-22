package core.basesyntax.service.Exceptions;

public class RegistrationSameUserException extends RegistrationServiceException{
    public RegistrationSameUserException(String message) {
        super(message);
    }
}
