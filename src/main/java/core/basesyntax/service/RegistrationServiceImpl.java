package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 18;
    private static final int MIN_NUMBER_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is invalid. Please enter the data");
        }
        if (user.getAge() == null || user.getAge() < MAX_AGE) {
            throw new InvalidDataException("The user must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("A user with this login already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_NUMBER_CHARACTERS) {
            throw new InvalidDataException("The login is invalid");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_NUMBER_CHARACTERS) {
            throw new InvalidDataException("The password is invalid");
        }
        return storageDao.add(user);
    }
}
