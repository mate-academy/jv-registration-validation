package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 90;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User should be not null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with same login exist");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidDataException("Login should be not null or not empty");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age must be not null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password should be not null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new InvalidDataException("Age should be more than "
                    + MIN_AGE
                    + " and less than "
                    + MAX_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password should be more than " + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
