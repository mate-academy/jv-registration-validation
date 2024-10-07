package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final static int MIN_LENGTH = 6;
    private final static int MIN_AGE = 18;

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User is null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null.");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login length must be at least 6 characters.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password length must be at least 6 characters.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least 18 years old.");
        }
        storageDao.add(user);
        return user;
    }
}