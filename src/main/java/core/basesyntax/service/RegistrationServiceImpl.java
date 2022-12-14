package core.basesyntax.service;

import core.basesyntax.Exception.InvalidInputDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Age can't be null");
        }
        if (user.getAge() < 18) {
            throw new InvalidInputDataException("Age should be 18 or higher");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputDataException("Password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidInputDataException("Password length should be at least 6 characters long");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputDataException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException("User with such login already exists");
        }
        return storageDao.add(user);
    }
}
