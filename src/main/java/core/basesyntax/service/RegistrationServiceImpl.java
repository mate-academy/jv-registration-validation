package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.InsecurePasswordException;
import core.basesyntax.service.exceptions.NullUserFieldException;
import core.basesyntax.service.exceptions.UserAlreadyExistsException;
import core.basesyntax.service.exceptions.UserTooYoungException;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User with such login already exists");
        }
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User mustn't be null");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new NullUserFieldException("User fields must have value");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new UserTooYoungException("Min user age is " + MIN_USER_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InsecurePasswordException("Min password length is " + MIN_PASSWORD_LENGTH);
        }
    }
}
