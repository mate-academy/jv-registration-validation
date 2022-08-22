package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.RegistrationDataException;
import core.basesyntax.service.exceptions.RegistrationPasswordException;
import core.basesyntax.service.exceptions.RegistrationSameUserException;
import core.basesyntax.service.exceptions.RegistrationSmallAgeException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!isValidData(user)) {
            throw new RegistrationDataException("Invalid input data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationSameUserException("User with login "
                    + user.getLogin() + " is already exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationSmallAgeException("Age less 18! \n Current age: "
                    + user.getAge());
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationPasswordException("Password must be more than 6 "
                    + "symbols! Actual length: " + user.getPassword().length());
        }
        return storageDao.add(user);
    }

    private boolean isValidData(User user) {
        if (user == null || user.getLogin() == null
                || user.getAge() == null || user.getAge() < 0
                || user.getPassword() == null
                || user.getPassword().length() <= 6) {
            return false;
        }
        return true;
    }
}
