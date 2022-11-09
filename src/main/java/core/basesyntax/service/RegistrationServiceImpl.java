package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final static int MIN_USER_AGE = 18;
    private final static int MAX_USER_AGE = 120;
    private final static int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login + " + user.getLogin() + " already exist, try other login");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User with age younger than " + MIN_USER_AGE + " years isn't allowed to register");
        }
        if (user.getAge() > MAX_USER_AGE) {
            throw new RuntimeException("User with age more than " + MAX_USER_AGE + " years isn't allowed to register");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be shorter than " + MIN_PASSWORD_LENGTH + " symbols");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        return storageDao.add(user);
    }
}
