package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null!");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with such login already exist!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters!");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Login should be at least "
                    + MIN_LOGIN_LENGTH + " characters!");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new InvalidDataException("Login should start with letter!");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidDataException("Not valid age: " + user.getAge()
                    + "User should be at least " + MIN_USER_AGE + " y.o.");
        }
        return storageDao.add(user);
    }
}
