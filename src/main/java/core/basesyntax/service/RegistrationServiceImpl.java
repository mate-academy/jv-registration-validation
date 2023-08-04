package core.basesyntax.service;

import static core.basesyntax.utils.UserValidator.checkAge;
import static core.basesyntax.utils.UserValidator.checkLogin;
import static core.basesyntax.utils.UserValidator.checkPassword;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }

    @Override
    public void clearStorage() {
        storageDao.deleteAll();
    }
}
