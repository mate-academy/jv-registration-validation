package core.basesyntax.middleware.user.stringparams.password;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.middleware.user.stringparams.StringParamsValidationMiddleware;
import core.basesyntax.model.User;

public class PasswordValidationMiddleware extends StringParamsValidationMiddleware {
    public PasswordValidationMiddleware(int minLength) {
        super(minLength);
    }

    @Override
    public void check(User user) {
        String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (password.length() < minLength) {
            throw new RegistrationException("Password should be at least "
                    + minLength + " characters long. Current length: " + password.length());
        }
        checkNext(user);
    }
}
