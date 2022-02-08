package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        notNullValidate(user);
        loginValidate(user);
        ageValidate(user);
        passwordValidate(user);
        storageDao.add(user);
        return user;
    }

    private boolean notNullValidate(User user) {
        if (user == null) {
            throw new RuntimeException("User can`t be null");
        }
        return true;
    }

    private User loginValidate(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Oops, your login should not be null");
        } else if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Oops, your login should not be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Oops, user with this login already registered");
        }
        return user;
    }

    private User ageValidate(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Oops, user`s age should not be null");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Oops, user`s minimum age should be at least 18 years");
        }
        return user;
    }

    private User passwordValidate(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Oops, your password should not be null");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Oops, your password should be at least "
                    + MIN_PASSWORD_LENGTH + " symbols length");
        }
        return user;
    }
}
