package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("Користувач не може бути null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "Користувач з таким логіном вже існує");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException(
                    "Логін повинен містити щонайменше " + MIN_LOGIN_LENGTH + " символів");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException(
                    "Пароль повинен містити щонайменше " + MIN_PASSWORD_LENGTH + " символів");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException(
                    "Користувач повинен бути не молодшим за " + MIN_AGE + " років");
        }
        return storageDao.add(user);
    }
}
