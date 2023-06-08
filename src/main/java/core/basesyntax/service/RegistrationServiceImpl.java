package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRED_LOGIN_LENGTH = 6;
    private static final int REQUIRED_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE_REQUIREMENTS = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (checkLogin(user.getLogin())
                && checkPassword(user.getPassword())
                && checkAge(user.getAge())) {
            storageDao.add(user);
        }
        return user;
    }

    public boolean checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException(
                    "Login can't be empty, login must be at list 6 characters long"
            );
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with such login is already registered");
        }
        if (login.length() < REQUIRED_LOGIN_LENGTH) {
            throw new RegistrationException("User login must be at least 6 characters long");
        }
        return true;
    }

    public boolean checkPassword(String password) {
        boolean passwordIsOk = true;
        if (password == null) {
            throw new RegistrationException("Password can't be empty");
        }
        if (password.length() < REQUIRED_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
        return passwordIsOk;
    }

    public boolean checkAge(Integer age) {
        boolean ageIsOk = true;
        if (age == null) {
            throw new RegistrationException("User age can't be empty or null");
        }
        if (age < MINIMUM_AGE_REQUIREMENTS) {
            throw new RegistrationException("User age must be at least 18");
        }
        return ageIsOk;
    }
}
