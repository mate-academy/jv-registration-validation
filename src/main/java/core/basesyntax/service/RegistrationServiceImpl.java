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
            throw new RuntimeException("Error: invalid user, user cannot be null");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Error: invalid password, password cannot be null");
        }

        if (user.getPassword().length() < ALLOWED_PASSWORD_LENGTH) {
            throw new RuntimeException("Error: invalid password length, "
                    + "user password must be at least" + ALLOWED_PASSWORD_LENGTH + "characters");
        }

        if (user.getAge() < ALLOWED_AGE) {
            throw new RuntimeException("Error: invalid age, age must be at least" + ALLOWED_AGE
                    + "years old");
        }

        if (user.getLogin() == null) {
            throw new RuntimeException("Error: login error, login cannot be empty");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error: user with such login allready exist "
                    + user.getLogin());
        }
        return storageDao.add(user);
    }
}
