package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 123;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User`s login must be not null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User`s password must be not null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User`s age must be not null");
        }
        if (user.getAge() < MIN_USER_AGE || user.getAge() > MAX_USER_AGE) {
            throw new RuntimeException("User`s age must be from "
                    + MIN_USER_AGE + " to " + MAX_USER_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User`s password length must be more than five symbols.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login " + user.getLogin() + " already exists.");
        }
        return storageDao.add(user);
    }
}
