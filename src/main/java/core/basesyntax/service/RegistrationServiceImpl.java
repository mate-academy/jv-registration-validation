package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationServiceException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMAL_AGE = 18;
    public static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        nullCheck(user);
        minAgeCheck(user);
        passwordLengthCheck(user);
        loginCheck(user);
        storageDao.add(user);
        return user;
    }

    void nullCheck(User user) {
        if (user == null) {
            throw new RegistrationServiceException("Register field is null!");
        }
    }

    void minAgeCheck(User user) {
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new RegistrationServiceException("Age must be more than 18!");
        }
    }

    void passwordLengthCheck(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationServiceException("Password length must be more than 6 symbols!");
        }
    }

    void loginCheck(User user) {
        if (user.getLogin() == null) {
           throw new RegistrationServiceException("Login field is empty!");
        }
        if (storageDao.get(user.getLogin()) != null) {
           throw new RegistrationServiceException("User with login " + user.getLogin()
                    + " already exist");
        }
    }
}
