package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidDataException;
import core.basesyntax.exeptions.UserIsNullException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_AGE = 18;
    private final static int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserIsNullException("User can't be null");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Invalid user age");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("Invalid user login " + user.getLogin()
                    + ". Login must be bigger than " + MIN_LENGTH + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Invalid user password " + user.getPassword()
                    + ". Password must be bigger than " + MIN_LENGTH + " characters");
        }
        if (storageDao.get(user.getLogin()).equals(user)) {
            throw new InvalidDataException("User with this login is already exist");
        }
        return storageDao.add(user);
    }
}
