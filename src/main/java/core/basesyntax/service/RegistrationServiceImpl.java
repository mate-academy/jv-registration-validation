package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("User can't be null.");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationDataException("Sorry, a user with that "
                    + "login already exists.");
        }

        if (Objects.isNull(user.getLogin()) || user.getLogin().length() < 6) {
            throw new InvalidRegistrationDataException("Login must have at least 6 characters.");
        }

        if (Objects.isNull(user.getPassword()) || user.getPassword().length() < 6) {
            throw new InvalidRegistrationDataException("Password must have at "
                    + "least 6 characters.");
        }

        if (user.getAge() < 18) {
            throw new InvalidRegistrationDataException("You need be at least 18 years old "
                    + "for registration.");
        }
        storageDao.add(user);
        return user;
    }
}
