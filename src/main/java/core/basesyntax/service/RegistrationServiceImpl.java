package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE_USER = 18;

    @Override
    public User register(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        String login = user.getLogin();
        String password = user.getPassword();
        int age = user.getAge();
        if (age >= MIN_AGE_USER
                && password.length() >= MIN_LENGTH_PASSWORD
                && storageDao.get(login) == null
                && login.matches("^[a-zA-Z0-9]+$")
                && password.matches("^[a-zA-Z0-9]+$")) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Incorrect data!");
    }
}
