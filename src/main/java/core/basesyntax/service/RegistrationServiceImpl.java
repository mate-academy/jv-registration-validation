package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_DATA = 6;

    @Override
    public User register(User user) {
        if (checkLoginIfPresent(user) != null ) {
            throw new InvalidDataException("User with this login has been already registered");
        } else if (user.getAge() == null) {
            throw new InvalidDataException("Age must not be null");
        } else if (user.getLogin() == null) {
            throw new InvalidDataException("Login mustn't be null");
        } else if (user.getPassword() == null) {
            throw new InvalidDataException("Password mustn't be null");
        } else if (user.getLogin().length() < MIN_LENGTH_DATA) {
            throw new InvalidDataException("Login must contain at least 6 characters");
        } else if (user.getPassword().length() < MIN_LENGTH_DATA) {
            throw new InvalidDataException("Password must contain at least 6 characters");
        } else if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("The user is not grown-up");
        }
        return storageDao.add(user);
    }

    private User checkLoginIfPresent(User user) {
        String login = user.getLogin();
        return storageDao.get(login);
    }
}
