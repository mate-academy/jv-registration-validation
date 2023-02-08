package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Invalid data. Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Invalid data. Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Invalid data. Age can't be null");
        }
        if (user.getLogin().equals("")) {
            throw new RegistrationException("Invalid data. "
                    + "Login can't be initialised with empty line");
        }
        if (user.getPassword().equals("")) {
            throw new RegistrationException("Invalid data. "
                    + "Password can't be initialised with empty line");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Invalid login. "
                    + "User with such login is already registered");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Invalid user  age. "
                    + "Age of the user should be 18 or more");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid password length. "
                    + "Password length should be not less than " + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }
}
