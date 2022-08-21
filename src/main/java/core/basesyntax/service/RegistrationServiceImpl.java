package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 4;
    private static final int MAXIMUM_LOGIN_LENGTH = 30;
    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_AGE = 150;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MAXIMUM_PASSWORD_LENGTH = 30;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();
        if (login == null) {
            throw new NullPointerException("User login can not be NULL");
        }
        if (password == null) {
            throw new NullPointerException("User password can not be NULL");
        }
        if (age == null) {
            throw new NullPointerException("User age can not be NULL");
        }
        if (login.length() < MINIMUM_LOGIN_LENGTH || login.length() > MAXIMUM_LOGIN_LENGTH) {
            throw new RuntimeException("User login must be between " + MINIMUM_LOGIN_LENGTH
                    + " and " + MAXIMUM_LOGIN_LENGTH + " letters but is " + login.length());
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH
                || password.length() > MAXIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("User password must be between " + MINIMUM_PASSWORD_LENGTH
                    + " and " + MAXIMUM_PASSWORD_LENGTH + " letters but is " + password.length());
        }
        if (age < MINIMUM_AGE || age > MAXIMUM_AGE) {
            throw new RuntimeException("User age can not be least " + MINIMUM_AGE
                    + " years old and can not be greater than " + MAXIMUM_AGE
                    + " years old but is " + age);
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("The user already exists in the storage");
        }
        storageDao.add(user);
        return user;
    }
}
