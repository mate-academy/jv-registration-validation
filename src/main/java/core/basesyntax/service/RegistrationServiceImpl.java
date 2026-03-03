package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationFailException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationFailException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin()
                .length() < MIN_LENGTH) {
            throw new RegistrationFailException("Login must be greater or equal than 6 characters");
        }
        if (user.getPassword() == null || user.getPassword()
                .length() < MIN_LENGTH) {
            throw new RegistrationFailException(
                    "Password must be greater or equal than 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationFailException("Age must be greater or equal than 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailException("Login already exists");
        }
        storageDao.add(user);
        return user;
    }

}


