package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfDataIsNull(user);
        storageDao.add(checkIfAdd(user));
        return user;
    }

    private void checkIfDataIsNull(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can not be null");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Password can not be null");
        }

        if (user.getAge() == null) {
            throw new RuntimeException("Age can not be null");
        }
    }

    private User checkIfAdd(User user) {
        if (!(Storage.people.contains(user)) && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_LENGTH) {
            return user;
        } else {
            throw new RuntimeException("Invalid data");
        }
    }
}
