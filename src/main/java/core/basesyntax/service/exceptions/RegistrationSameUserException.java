package core.basesyntax.service.exceptions;

public class RegistrationSameUserException extends RegistrationServiceException {
    public RegistrationSameUserException(String message) {
        super(message);
    }
}
