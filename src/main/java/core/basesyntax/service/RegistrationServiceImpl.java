package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AgeValidationException;
import core.basesyntax.exception.LoginValidationException;
import core.basesyntax.exception.PasswordValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginValidationException("Login can't be filled");
        }
        if (user.getAge() < MIN_AGE) {
            throw new AgeValidationException("Age can't be less than " + MIN_AGE);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new PasswordValidationException("Password can't be less than "
            + MIN_LENGTH_PASSWORD);
        }
        return storageDao.add(user);
    }
}
