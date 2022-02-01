package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Bad age");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password too shortly");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login already exist");
        }
        return storageDao.add(user);
    }
}
