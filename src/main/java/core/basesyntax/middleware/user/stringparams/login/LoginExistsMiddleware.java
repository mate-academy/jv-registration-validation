package core.basesyntax.middleware.user.stringparams.login;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.middleware.Middleware;
import core.basesyntax.model.User;

public class LoginExistsMiddleware extends Middleware {
    @Override
    public void check(User user) {
        for (User inStorage : Storage.people) {
            if (user.getLogin().equals(inStorage.getLogin())) {
                throw new RegistrationException("User with such email already exists");
            }
        }
        checkNext(user);
    }
}
