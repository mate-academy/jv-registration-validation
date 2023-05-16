package core.basesyntax.middleware.user.stringparams.login;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.middleware.user.stringparams.StringParamsValidationMiddleware;
import core.basesyntax.model.User;

public class LoginValidationMiddleware extends StringParamsValidationMiddleware {
    public LoginValidationMiddleware(int minLength) {
        super(minLength);
    }

    @Override
    public void check(User user) {
        String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (login.length() < minLength) {
            throw new RegistrationException("Password should be at least "
                    + minLength + " characters long. Current length: " + login.length());
        }
        checkNext(user);
    }
}
