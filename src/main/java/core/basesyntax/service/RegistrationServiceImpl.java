package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHAR_FOR_LOGIN_PASSWORD = 6;
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 120;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (isNotValidLogin(user) || isNotValidPassword(user) || isNotValidAge(user)) {
            throw new RegistrationException("User data such as login: "
                    + user.getLogin()
                    + ", password: "
                    + user.getPassword()
                    + " or age: "
                    + user.getAge() + " is incorrect.");
        }

        if (isLoginExist(user)) {
            throw new RegistrationException("User with login "
                    + user.getLogin()
                    + " already exist");
        }

        return storageDao.add(user);
    }

    private boolean isLoginExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean isNotValidAge(User user) {
        return user.getAge() < MIN_USER_AGE || user.getAge() > MAX_USER_AGE;
    }

    private boolean isNotValidPassword(User user) {
        return user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getPassword().length() < MIN_CHAR_FOR_LOGIN_PASSWORD;
    }

    private boolean isNotValidLogin(User user) {
        return user.getLogin() == null
                || user.getLogin().isEmpty()
                || user.getLogin().length() < MIN_CHAR_FOR_LOGIN_PASSWORD;
    }

}
