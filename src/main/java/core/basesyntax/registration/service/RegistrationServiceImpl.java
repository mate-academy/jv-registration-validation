package core.basesyntax.registration.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements Registration {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exist");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Password should be at lest "
                    + MIN_LOGIN_LENGTH + " characters");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new InvalidDataException("Password should starts with letter");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User's age should be at least "
                    + MIN_AGE + " years old");
        }
        return storageDao.add(user);
    }
}
