package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CantAddUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CantAddUserException {
        if (storageDao.get(user.getLogin()) == null
                && user.getLogin().length() >= 6
                && user.getPassword().length() >= 6
                && user.getAge() >= 18) {
            storageDao.add(user);
            return user;
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CantAddUserException("User already exists in system");
        } else if (user.getLogin().length() < 6) {
            throw new CantAddUserException("Login too short");
        } else if (user.getPassword().length() < 6) {
            throw new CantAddUserException("Password too short");
        }
        throw new CantAddUserException("User's age under 18");
    }
}
