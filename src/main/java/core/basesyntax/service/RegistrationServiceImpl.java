package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.excpt.NotValidUserData;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidUserData("There is already a user with the given name");
        }
        if (user.getLogin().length() < 6) {
            throw new NotValidUserData("User login must have at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new NotValidUserData("User password must have at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new NotValidUserData("User must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
