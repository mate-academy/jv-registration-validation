package core.basesyntax.service;

import core.basesyntax.RegistrationFailedException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 130;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RegistrationFailedException("User can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationFailedException("User age can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationFailedException("User login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationFailedException("User password can't be null");
        }
        if (user.getLogin().length() == 0) {
            throw new RegistrationFailedException("User login must be longer than 0 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationFailedException("User must be 18 y.o. or older");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RegistrationFailedException("User must be less than 130 years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationFailedException("Password length must be longer than 6");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailedException("This login is already in use, please try again");
        }
    }
}
