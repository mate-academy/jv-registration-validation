package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.UserRegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new UserRegisterException("Login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegisterException("This login is already registered");
        }
        if (user.getPassword() == null) {
            throw new UserRegisterException("Password can't be empty");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegisterException("Password can't be less than six characters");
        }
        if (user.getAge() == null) {
            throw new UserRegisterException("The age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegisterException("The age can`t be less than eighteen years");
        }
        return storageDao.add(user);
    }
}
