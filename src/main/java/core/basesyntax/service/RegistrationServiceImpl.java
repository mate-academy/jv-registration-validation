package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private final int MIN_AGE = 18;
    private final int MIN_LOGIN_LENGTH = 6;
    private final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }

        if (user.getLogin() == null) {
            throw new InvalidDataException("Login cannot be null");
        }

        if (user.getLogin().trim().isEmpty()
                || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Login length must be at least 6");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Login already exists");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("Password cannot be null");
        }

        if (user.getPassword().trim().isEmpty()
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password length must be at least 6");
        }

        if (user.getAge() == null) {
            throw new InvalidDataException("Age cannot be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("You must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
