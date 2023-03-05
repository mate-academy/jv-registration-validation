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
            throw new RuntimeException("User need to have age - 18 or more");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password should to contain 6 symbols or more");
        }
        if (storageDao.get(user.getLogin()) != null && storageDao.get(user.getLogin())
                .getLogin().equals(user.getLogin())) {
            throw new RuntimeException("User with such login already exist");
        }
        return storageDao.add(user);
    }
}
