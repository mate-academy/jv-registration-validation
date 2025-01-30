package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CustomException("User can't be null");
        }
        if (Storage.people.contains(user)) {
            throw new CustomException("User with this login already exists");
        }
        if (user.getLogin() == null) {
            throw new CustomException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new CustomException("Login should be at least " + MIN_LOGIN_LENGTH
                    + " characters");
        }
        if (user.getPassword() == null) {
            throw new CustomException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new CustomException("Password should be at least " + MIN_PASSWORD_LENGTH
                    + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new CustomException("Age should be at least " + MIN_AGE + " years");
        }
        storageDao.add(user);
        return user;
    }
}
