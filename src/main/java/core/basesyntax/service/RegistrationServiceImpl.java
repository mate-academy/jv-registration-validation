package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user.getLogin() == null) {
            throw new ValidationException("User login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new ValidationException("User login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("User password can't be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new ValidationException("User password can't be empty");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new ValidationException("User password cannot be less than 6 characters");
        }
        if (user.getAge() == null) {
            throw new ValidationException("User age can't be null");
        }
        if (user.getAge() < 0) {
            throw new ValidationException("User age must not be less than 0");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User age cannot be less than 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("The user with this login is already in the Storage");
        }
        return storageDao.add(user);
    }
}
