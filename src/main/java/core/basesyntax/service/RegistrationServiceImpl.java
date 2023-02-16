package core.basesyntax.service;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            return null;
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already registered");
        }
        if (user.getAge() == null) {
            return null;
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("The user's age is less than 18");
        }
        if (user.getPassword() == null) {
            return null;
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("The user's password is at least 6 characters");
        }
        storageDao.add(user);
        return user;
    }
}
