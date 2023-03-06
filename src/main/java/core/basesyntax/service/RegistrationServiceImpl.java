package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new NotValidUserException("User's age can't be null");
        }
        if (user.getLogin() == null) {
            throw new NotValidUserException("User's login can't be null");
        }
        if (user.getPassword() == null) {
            throw new NotValidUserException("User's password can't be null");
        }
        if (user.getLogin().length() == 0) {
            throw new NotValidUserException("User's login must be longer than 0");
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotValidUserException("User must be 18 years old or older");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new NotValidUserException("Password's length must be longer than 6");
        }
        for (User user1: Storage.people) {
            if (user1.getLogin() == user.getLogin()) {
                throw new NotValidUserException("Such a user already exists");
            }
        }
        return storageDao.add(user);
    }
}
