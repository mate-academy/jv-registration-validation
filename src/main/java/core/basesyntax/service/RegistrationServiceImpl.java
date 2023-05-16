package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.middleware.Middleware;
import core.basesyntax.middleware.user.UserNotNullMiddleware;
import core.basesyntax.middleware.user.age.AgeValidationMiddleware;
import core.basesyntax.middleware.user.stringparams.login.LoginExistsMiddleware;
import core.basesyntax.middleware.user.stringparams.login.LoginValidationMiddleware;
import core.basesyntax.middleware.user.stringparams.password.PasswordValidationMiddleware;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();
    private final Middleware middleware;

    {
        middleware = Middleware.link(
                new UserNotNullMiddleware(),
                new LoginValidationMiddleware(MIN_LENGTH),
                new PasswordValidationMiddleware(MIN_LENGTH),
                new AgeValidationMiddleware(MIN_AGE),
                new LoginExistsMiddleware()
        );
    }

    @Override
    public User register(User user) {
        middleware.check(user);
        return storageDao.add(user);
    }
}
