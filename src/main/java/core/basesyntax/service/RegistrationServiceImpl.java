package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        try {
            if (validate(user)) {
                storageDao.add(user);
                return user;
            } else {
                return null;
            }
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate(User user) throws InvalidDataException {
        if (user.getAge() < 18) {
            throw new InvalidDataException("User`s age must be at least 18!");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("User`s login length must be at least 6 symbols!");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("User`s password length must be at least 6 symbols!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with such login already exists!");
        }

        return true;
    }
}
