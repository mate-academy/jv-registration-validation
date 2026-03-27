package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationDataInvalidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationDataInvalidException("User does not exist");
        }

        if (user.getLogin() == null) {
            throw new RegistrationDataInvalidException("Login does not exist");
        }

        if (user.getPassword() == null) {
            throw new RegistrationDataInvalidException("Password does not exist");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationDataInvalidException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters long");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationDataInvalidException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters long");
        }

        if (user.getAge() == null) {
            throw new RegistrationDataInvalidException("Age does not exist");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationDataInvalidException(
                    "Age must be at least " + MIN_AGE
            );
        }

        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }

        throw new RegistrationDataInvalidException("Login already exists");
    }
}
