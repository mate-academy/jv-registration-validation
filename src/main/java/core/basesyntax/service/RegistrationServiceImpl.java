package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private static final Integer MIN_AGE = 18;
    private static final Integer MAX_AGE = 99;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputDataException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Age can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException("Login " + user.getLogin()
                    + " is already exists");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new InvalidInputDataException("The age should be at least 18 and less than 99");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputDataException("The password should be at least 6 symbols");
        }
        storageDao.add(user);
        return user;
    }
}
