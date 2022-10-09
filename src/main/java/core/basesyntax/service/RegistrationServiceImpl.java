package core.basesyntax.service;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MIN_AGE_FOR_REGISTER = 18;
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 125;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        checkAge(user);
        User findUser = storageDao.get(user.getLogin());
        if (findUser != null || user.getAge() < MIN_AGE_FOR_REGISTER
                || user.getPassword().length() < MIN_PASSWORD_SIZE) {
            return null;
        }
        storageDao.add(user);
        return user;
    }

    private void checkNull(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid user. User info can't be read");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid user login. Login can't be read");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Invalid user age. Age can't be read");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid user password. Password can't be read");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() <= MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("Invalid user age - " + user.getAge());
        }
    }
}
