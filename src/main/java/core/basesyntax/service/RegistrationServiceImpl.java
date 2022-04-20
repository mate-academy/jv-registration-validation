package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Object or field was null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age should be more than 18, but was: " + user.getAge());
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RuntimeException("Length password must be more than "
                    + MIN_PASSWORD + " , but was:" + user.getPassword().length());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login: " + user.getLogin() + " already used..");
        }
        storageDao.add(user);
        return user;
    }
}
