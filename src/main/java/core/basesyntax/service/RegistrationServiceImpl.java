package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 1;
    private static final int MIN_AGE = 18;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Invalid data");
        }
        if (storageDao.get(user.getLogin()) == null
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && user.getAge() >= MIN_AGE
                && user.getLogin().length() >= MIN_LOGIN_LENGTH) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Can't register user with these parameters");
    }
}
