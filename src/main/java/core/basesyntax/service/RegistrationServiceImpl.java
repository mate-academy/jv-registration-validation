package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null");
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("This login already exist.");
        }

        if (user.getLogin().length() < 6) {
            throw new ValidationException("User login should be at least 6 characters");
        }

        if (user.getPassword().length() < 6) {
            throw new ValidationException("User password should be at least 6 characters");
        }

        if (user.getAge() < 18) {
            throw new ValidationException("User age should be more than 18 years");
        }

        return storageDao.add(user);
    }
}

