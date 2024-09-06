package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import core.basesyntax.validation.UserValidator;
import core.basesyntax.validation.UserValidatorImpl;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final UserValidator userValidator = new UserValidatorImpl();

    @Override
    public User register(User user) {
        try {
            userValidator.validate(user);
            if (storageDao.get(user.getLogin()) != null) {
                throw new UserValidationException("User with this login already exists");
            }
            storageDao.add(user);
            return user;
        } catch (UserValidationException exception) {
            System.err.println("Registration failed: " + exception.getMessage());
            throw new RuntimeException("Registration failed", exception);
        }
    }
}
