package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        this(new StorageDaoImpl());
    }

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new NullPointerException("Data cannot be null");
        }

        if (user.getAge() < 18) {
            throw new InvalidInputDataException("Age cannot be less than 18 years");
        }

        if (user.getPassword().length() < 6) {
            throw new InvalidInputDataException("Password cannot be less than 6 characters");
        }

        if (user.getLogin().length() < 6) {
            throw new InvalidInputDataException("Login cannot be less than 6 characters");
        }

        for (User existingUser : Storage.people) {
            if (existingUser.getLogin().equals(user.getLogin())) {
                throw new InvalidInputDataException("User with such login has already exist");
            }
        }
        return storageDao.add(user);
    }
}
