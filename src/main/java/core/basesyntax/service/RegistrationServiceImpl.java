package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_ALLOWED_AGE = 18;
    private static final int MAX_ALLOWED_AGE = 125;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can`t be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("Login can`t be empty");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can`t be null");
        }
        if (user.getAge() < MIN_ALLOWED_AGE) {
            throw new InvalidDataException("Age is less than " + "MIN_ALLOWED_AGE");
        }
        if (user.getAge() > MAX_ALLOWED_AGE) {
            throw new InvalidDataException("Age is bigeer than " + "MAX_ALLOWED_AGE");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can`t be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Your password is shorter than "
                    + "MIN_PASSWORD_LENGTH");
        }
        if (user.getPassword().isEmpty()) {
            throw new InvalidDataException("Password can`t be empty");
        }
        return storageDao.add(user);
    }
}
