package core.basesyntax.service;

import core.basesyntax.UserValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new UserValidationException("User login is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("User with this login already exist");
        }
        if (user.getLogin().length() < 6) {
            throw new UserValidationException("User login should be at least 6 characters");
        }

        if (user.getPassword() == null) {
            throw new UserValidationException("User password is null");
        }
        if (user.getPassword().length() < 6) {
            throw new UserValidationException("User password should be at least 6 characters");
        }

        if (user.getAge() == null) {
            throw new UserValidationException("User age is null");
        }

        if (user.getAge() < 18) {
            throw new UserValidationException("User age should be greater than 18");
        }
        return storageDao.add(user);
    }
}
