package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login: "
                    + user.getLogin() + " already exists");
        }

        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age should be 18 and older, but is: " + user.getAge());
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password`s length should be " + MIN_PASSWORD_LENGTH
                    + " or more, but is: " + user.getPassword().length());
        }

        return storageDao.add(user);
    }
}
