package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    protected static final int MIN_PASSWORDS_LENGTH = 6;
    protected static final int MIN_USERS_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User shouldn't be a null");
        }

        if (user.getLogin() == null) {
            throw new NullPointerException("Users login shouldn't be a null");
        }

        if (user.getAge() == null) {
            throw new NullPointerException("Users age should be more than "
                    + MIN_USERS_AGE + " y.o");
        }

        if (user.getAge() < MIN_USERS_AGE) {
            throw new RuntimeException("Users age should be more than "
                    + MIN_USERS_AGE + " y.o");
        }

        if (user.getPassword() == null) {
            throw new NullPointerException("Users password shouldn't be a null ");
        }

        if (user.getPassword().length() < MIN_PASSWORDS_LENGTH) {
            throw new RuntimeException("Users password should have more than "
                    + MIN_PASSWORDS_LENGTH + " characters");
        }

        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new RuntimeException("User with such login already exist."
                        + " Users login should have unique value");
            }
        }
        storageDao.add(user);
        return user;
    }
}
