package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int LOG_OR_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new InvalidDataException("User's login can't be null");
        }

        if (checkIfUserAlreadyExist(user)) {
            throw new InvalidDataException("User with " + user.getLogin() + " already exists");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("User's password can't be null");
        }

        if (user.getAge() == null) {
            throw new InvalidDataException("User's age can't be null");
        }

        if (user.getLogin().length() < LOG_OR_PASS_LENGTH) {
            throw new InvalidDataException("Not valid login: " + user.getLogin()
                    + ". Min allowed length for login is " + LOG_OR_PASS_LENGTH);
        }

        if (user.getPassword().length() < LOG_OR_PASS_LENGTH) {
            throw new InvalidDataException("Not valid password: " + user.getPassword()
                    + ". Min allowed length for password is " + LOG_OR_PASS_LENGTH);
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Not valid age " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        return storageDao.add(user);
    }

    private boolean checkIfUserAlreadyExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
