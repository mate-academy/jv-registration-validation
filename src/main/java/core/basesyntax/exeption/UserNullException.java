package core.basesyntax.exeption;

public class UserNullException extends RegistrationServiceException {
    public UserNullException() {
        super("This user is null");
    }
}
