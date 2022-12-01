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
        if (user == null) {
            throw new RuntimeException("ERROR! User is a living person, user cannot be null.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("ERROR! User login cannot be null.");
        }
        if (user.getLogin().equals("")) {
            throw new RuntimeException("ERROR! User login cannot be empty.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("ERROR! User \""
                    + user.getLogin()
                    + "\" already exist in the storage.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("ERROR! This storage is for adult users only, sorry mate.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("ERROR! User password must be at least 6 characters long.");
        }
        storageDao.add(user);
        return user;
    }
}
