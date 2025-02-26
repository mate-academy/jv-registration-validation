package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.DataExceptions;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws DataExceptions {
        if (storageDao.get(user.getLogin()) != null) {
            throw new DataExceptions("Login is already used");
        }
        if (user.getLogin().length() < 6) {
            throw new DataExceptions("Login is too short");
        }
        if (user.getPassword().length() < 6) {
            throw new DataExceptions("Password is too short");
        }
        if (user.getAge() < 18) {
            throw new DataExceptions("The User is too young");
        }

        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user);

        return user;
    }
}
