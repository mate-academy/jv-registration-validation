package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("User data can't be null");
        }
        if (!checkUserExists(user.getLogin())
                && checkAge(user.getAge())
                && checkPassword(user.getPassword())) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Invalid input data for user " + user);
    }

    private boolean checkUserExists(String login) {
        return storageDao.get(login) != null;
    }

    private boolean checkAge(int age) {
        return age >= MIN_AGE;
    }

    private boolean checkPassword(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }
}
