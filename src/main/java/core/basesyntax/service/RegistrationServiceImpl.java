package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isLoginIsNull(user)) {
            throw new NullPointerException("Password can't be null");
        }
        if (isNotCorrectPassword(user)) {
            throw new RuntimeException("Password should be 6 or more symbols");
        }
        if (isNotCorrectAge(user)) {
            throw new RuntimeException("User can't be register because "
                    + "his age less than 18 years");
        }
        if (Storage.people.size() != 0) {
            checkExistingUser(user);
        }
        storageDao.add(user);
        return user;
    }

    private void checkExistingUser(User user) {
        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("There is already a user with this login");
            }
        }
    }

    private boolean isNotCorrectAge(User user) {
        return user.getAge() < MIN_AGE;
    }

    private boolean isNotCorrectPassword(User user) {
        return user.getPassword().length() < MIN_LENGTH_OF_PASSWORD;
    }

    private boolean isLoginIsNull(User user) {
        return user.getLogin().isEmpty();
    }
}
