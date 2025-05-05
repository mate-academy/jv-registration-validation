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
        validateUser(user);
        validatePassword(user);
        validateAge(user);
        validateUnique(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("The user in null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("The users login in null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("The users age in null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("The users password in null");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < 0) {
            throw new RuntimeException("The age is negative");
        }
        if (user.getAge() == 0) {
            throw new RuntimeException("The age is zero");
        }
        if (user.getAge() < MAX_AGE) {
            throw new RuntimeException("The user is too young");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("The password is too short");
        }
    }

    private void validateUnique(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such user is already exist");
        }
    }
}
