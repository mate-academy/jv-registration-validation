package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.UserAlreadyExistsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("Invalid data type provided.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User with the same login already exists.");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("You should be at least 18 years old.");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Your password should be at least 6 characters long.");
        }

        storageDao.add(user);
        return user;
    }
}
