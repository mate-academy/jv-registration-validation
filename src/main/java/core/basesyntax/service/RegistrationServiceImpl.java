package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUsersDataException("Login must have more than 5 symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ExistingLoginException("user with this login already exist");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUsersDataException("Password must have more than 5 symbols");
        }
        if (user.getAge() < 18 || user.getAge() > 110) {
            throw new InvalidUsersDataException("Age must be between 18-110 years");
        }
        storageDao.add(user);
        return user;
    }
}
