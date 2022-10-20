package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE_REQUIRED = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();
        if (!checkLogin(login)) {
            throw new RuntimeException("Such user is already registered");
        }
        if (!checkPassword(password)) {
            throw new RuntimeException("Password length should be greater than "
                    + MINIMUM_PASSWORD_LENGTH);
        }
        if (!checkAge(age)) {
            throw new RuntimeException("User age should be greater then "
                    + MINIMUM_AGE_REQUIRED);
        }
        return storageDao.add(user);
    }

    private <T> boolean fieldIsNull(T field) {
        return field == null;
    }

    private boolean checkLogin(String login) {
        if (fieldIsNull(login)) {
            throw new RuntimeException("Login can't be null");
        }
        if (login.isBlank()) {
            throw new RuntimeException("Login can't be blank");
        }
        User check = storageDao.get(login);
        return check == null;
    }

    private boolean checkPassword(String password) {
        if (fieldIsNull(password)) {
            throw new RuntimeException("Password can't be null");
        }
        if (password.contains(" ")) {
            throw new RuntimeException("Password can't contain spaces or be blank");
        }
        return password.length() >= MINIMUM_PASSWORD_LENGTH;
    }

    private boolean checkAge(Integer age) {
        if (fieldIsNull(age)) {
            throw new RuntimeException("Age can't be null");
        }
        return age >= MINIMUM_AGE_REQUIRED;
    }
}
