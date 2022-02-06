package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Please write your login and password");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User " + user.getLogin() + " already exists");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password must be at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("You must be at least 18 years old");
        }
        return storageDao.add(user);
    }
}
