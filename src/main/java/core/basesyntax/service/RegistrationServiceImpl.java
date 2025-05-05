package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("The user should be at least "
                    + MINIMAL_AGE + " years old.");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should be at least "
                    + MINIMAL_PASSWORD_LENGTH + " characters.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be empty!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user with such login already exists. "
                    + "Please, enter another login.");
        }
        return storageDao.add(user);
    }
}
