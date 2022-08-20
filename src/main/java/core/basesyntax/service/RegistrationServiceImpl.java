package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("User`s age cannot be less than 18 and more than 100");
        }
        if (user.getId() < 0 || user.getId() == null) {
            throw new RuntimeException("User`s id cannot be negative or empty");
        }
        if (user.getLogin().length() == 0 || user.getLogin() == null) {
            throw new RuntimeException("User`s login cannot be empty");
        }
        if (user.getPassword().length() < 6 || user.getPassword() == null) {
            throw new RuntimeException("Password length cannot be less than 6 "
                    + "and password cannot be null");
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        } else {
            throw new RuntimeException("User by login: " + user.getLogin() + " already exists");
        }
    }
}
