package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.AlreadyExistsException;
import core.basesyntax.service.exception.InvalidAgeException;
import core.basesyntax.service.exception.InvalidLoginException;
import core.basesyntax.service.exception.InvalidPasswordException;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new AlreadyExistsException("User already exists: "
                    + user.getLogin());
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidLoginException("Login is too short, "
                    + "must be at least 6 character long");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidPasswordException("Password is too short, "
                    + "must be at least 6 character long");
        }
        if (user.getAge() < 18) {
            throw new InvalidAgeException("Age must be at least 18");
        }
        Storage.people.add(user);
        return user;
    }
}
