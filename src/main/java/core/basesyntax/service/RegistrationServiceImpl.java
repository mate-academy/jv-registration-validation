package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (userIsNull(user)) {
            throw new RuntimeException("User and all it fields can't be null!!!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login is already exist");
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RuntimeException("Password can't be shorter than "
                   + MIN_LENGTH_OF_PASSWORD + " symbol");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User's age should be more than " + MIN_AGE);
        }
        storageDao.add(user);
        return user;
    }

    private boolean userIsNull(User user) {
        return (user == null || user.getId() == null || user.getAge() == null
                || user.getLogin() == null || user.getPassword() == null);
    }
}
