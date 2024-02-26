package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_ALLOWED_AGE = 18;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    + ". Login can't be less than" + MIN_LOGIN_LENGTH + "characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not valid password: "
                    + user.getPassword() + ". Password can't be less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_ALLOWED_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_ALLOWED_AGE);
        }
        User foundUser = storageDao.get(user.getLogin());
        if (foundUser != null && foundUser.equals(user)) {
            throw new RegistrationException("The user already exists");
        }
        return storageDao.add(user);
    }
}
