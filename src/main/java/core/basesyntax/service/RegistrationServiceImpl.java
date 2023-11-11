package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User already exists");
        }

        if (user.getLogin().length() >= 6 && user.getPassword().length() >= 6
                && user.getAge() >= 18) {
            try {
                storageDao.add(user);
                return user;
            } catch (RuntimeException e) {
                throw new InvalidUserDataException("Error during user registration: "
                        + e.getMessage());
            }
        } else {
            throw new InvalidUserDataException("Invalid user data");
        }
    }
}
