package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LETTERS_IN_PASSWORD = 7;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserAge(user);
        validateUserPassword(user);
        validateUserLogin(user);
        return storageDao.add(user);
    }

    private void validateUserAge(User user) {
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("The age should be 18 or more and less then 100");
        }
    }

    private void validateUserPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LETTERS_IN_PASSWORD) {
            throw new RuntimeException("The password should contain at least 6 characters"
                    + " and don't contain null");
        }
    }

    private void validateUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("The login should consist of letters and numbers only");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The same login has already existed");
        }
    }
}
