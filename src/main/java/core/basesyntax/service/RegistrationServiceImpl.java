package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 18) {
            throw new InvalidDataException("Your age is under 18!");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Your password is less than 6 characters!");
        }
        for (User userToCompare : Storage.people) {
            if (userToCompare.getLogin().equals(user.getLogin())) {
                throw new InvalidDataException("User with such a login already exists!");
            }
        }

        storageDao.add(user);
        return user;
    }
}
