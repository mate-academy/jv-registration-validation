package core.basesyntax.middleware.user;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.middleware.Middleware;
import core.basesyntax.model.User;

public class UserNotNullMiddleware extends Middleware {
    @Override
    public void check(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        checkNext(user);
    }
}
