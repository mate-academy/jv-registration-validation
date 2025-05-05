package core.basesyntax.middleware.user.stringparams.login;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.middleware.Middleware;
import core.basesyntax.model.User;

public class LoginExistsMiddleware extends Middleware {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void check(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such email already exists");
        }
        checkNext(user);
    }
}
