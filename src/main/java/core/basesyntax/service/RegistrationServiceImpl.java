package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AgeIsIncorrectException;
import core.basesyntax.exceptions.LoginIsIncorrectException;
import core.basesyntax.exceptions.PassworsIsIncorrectException;
import core.basesyntax.exceptions.UserIsNullException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOWED_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("User should not equal null");
        }
        if (storageDao.get(user.getLogin()) != null || user.getLogin() == null) {
            throw new LoginIsIncorrectException("User with this login is already in base");
        }
        if (user.getAge() == null || user.getAge() < ALLOWED_AGE) {
            throw new AgeIsIncorrectException("User age should be filled "
                    + "and should be more than 18 years");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new PassworsIsIncorrectException("Password should be filled "
                    + "and contain at least 6 characters");
        }
        storageDao.add(user);
        return user;
    }
}
