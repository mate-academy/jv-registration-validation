package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("Login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("This person already exist");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User can't be younger than 18");
        }
        if (user.getAge() > MAX_AGE) {
            throw new InvalidDataException("User can't be older than 100");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new InvalidDataException("You can't have password shorter than 6");
        }
        if (user.getPassword().isEmpty()) {
            throw new InvalidDataException("Password can't be empty");
        }
        return storageDao.add(user);
    }
}
