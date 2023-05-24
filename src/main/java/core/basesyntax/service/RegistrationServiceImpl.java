package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Please enter your login");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Please enter your password");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Please enter your age");
        }
        for (User eachUser : Storage.people) {
            if (eachUser.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("This login is already use: " + user.getLogin());
            }
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("User's login must be at least " + MIN_LENGTH
                    + " character long, but was " + user.getLogin().length());
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("User's password must be at least " + MIN_LENGTH
                    + " character long, but was " + user.getLogin().length());
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be minimum " + MIN_AGE
                    + " but was " + user.getAge());
        }
        storageDao.add(user);
        return user;
    }
}
