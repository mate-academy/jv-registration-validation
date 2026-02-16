package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }

        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }

        // Check if user already exists
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already exists");
        }

        // Check if login is at least 6 characters long
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("Login must be at least 6 characters");
        }

        // Check if password is at least 6 characters long
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Password must be at least 6 characters");
        }

        // Check if user is at least 18
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User must be at least 18");
        }

        return storageDao.add(user);
    }
}
