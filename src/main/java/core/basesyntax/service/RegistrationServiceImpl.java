package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        validateUserParametersNotNull(user);
        checkParameters(user);
        checkExistingUser(user);

        return storageDao.add(user);
    }

    private void validateUserParametersNotNull(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
    }

    private void checkParameters(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        if (user.getLogin().length() < MIN_LENGTH || user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Login and password must be at least 6 characters long");
        }
    }

    private void checkExistingUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exists");
        }
    }
}
