package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOWED_AGE = 18;
    private static final int ALLOWED_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException();
        }

        if (user.getPassword().length() < ALLOWED_PASSWORD_LENGTH) {
            throw new RuntimeException("Error: invalid password length, "
                    + "user password must be at least 6 characters");
        }

        if (user.getAge() < ALLOWED_AGE) {
            throw new RuntimeException("Error: invalid age, age must be at least 18 years old");
        }

        if (user.getLogin() == null) {
            throw new RuntimeException("Error: login error, login cannot be empty");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error: user with such login allready exist.");
        }
        return storageDao.add(user);
    }
}
