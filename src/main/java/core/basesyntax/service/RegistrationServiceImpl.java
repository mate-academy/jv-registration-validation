package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkPassword(User user) {
        String password = user.getPassword();
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new RuntimeException("Password is empty. Input your password");
        }
        if (user.getPassword().equals(user.getPassword().toLowerCase())) {
            throw new RuntimeException("Password should have at least one uppercase letter");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is short, input at least 6 character");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Your can`t be registered with this age");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Can`t register login. Account is already exist");
        }
    }

}
