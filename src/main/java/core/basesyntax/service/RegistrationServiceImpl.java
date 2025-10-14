package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private final int minAge = 18;
    private final int minLoginLength = 6;
    private final int minPasswordLength = 6;

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
                || user.getLogin().length() < minLoginLength) {
            throw new InvalidDataException("Login length must be at least 6");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Login already exists");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("Password cannot be null");
        }

        if (user.getPassword().trim().isEmpty()
                || user.getPassword().length() < minPasswordLength) {
            throw new InvalidDataException("Password length must be at least 6");
        }

        if (user.getAge() == null) {
            throw new InvalidDataException("Age cannot be null");
        }

        if (user.getAge() < minAge) {
            throw new InvalidDataException("You must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
