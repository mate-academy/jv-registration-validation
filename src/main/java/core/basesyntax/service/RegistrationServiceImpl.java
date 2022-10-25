package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        checkPassword(user);
        checkAge(user);
        checkRepeat(user);
        storageDao.add(user);
        return user;
    }

    private void checkNull(User user) {
        if (user == null) {
            throw new RuntimeException("The user in null");
        }
    }

    private void checkPassword(User user) {
        if (user.getAge() < MAX_AGE) {
            throw new RuntimeException("The user is too young");
        }
    }

    private void checkAge(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("The password is too short");
        }
    }

    private void checkRepeat(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such user is already exist");
        }
    }
}
