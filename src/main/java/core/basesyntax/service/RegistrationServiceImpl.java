package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 123; // Max age registered on Earth was 122 years
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new InvalidDataException("Invalid data. Fields can't be null");
        }
        if (user.getLogin().equals("") || user.getPassword().equals("")) {
            throw new InvalidDataException("Invalid data. "
                    + "Login or Password can't be initialised with empty line");
        }
        for (User users : Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new InvalidDataException("Invalid login. "
                        + "User with such login is already registered");
            }
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new InvalidDataException("Invalid user  age. "
                    + "Age of the user should be 18 or more");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Invalid password length. "
                    + "Password length should be not less than " + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
