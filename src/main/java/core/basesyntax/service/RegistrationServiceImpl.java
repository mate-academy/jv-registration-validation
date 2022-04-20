package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storage = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Incorrect data");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("The user must be" + MINIMUM_AGE + " years of age or older");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be at least " + MIN_PASSWORD_LENGTH);
        }
        if (storage.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exists");
        }
        return storage.add(user);
    }
}
