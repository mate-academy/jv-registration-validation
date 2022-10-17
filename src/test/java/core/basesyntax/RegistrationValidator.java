package core.basesyntax;

public class RegistrationValidator implements EmailValidator {

    @Override
    public boolean emailIsValid(String email) {
        return false;
    }

    @Override
    public boolean passwordIsValid(String password) {
        return false;
    }
}
