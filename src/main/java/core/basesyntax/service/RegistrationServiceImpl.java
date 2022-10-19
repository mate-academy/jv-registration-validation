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
            throw new RuntimeException("Login can`t be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can`t be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can`t be null");
        }
        String userLogin = user.getLogin();
        User userFromStorage = storageDao.get(userLogin);
        if (userFromStorage != null) {
            throw new RuntimeException("Login like this already exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age can`t be less than 18");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Invalid password (password length can`t be less than 6)");
        }
        storageDao.add(user);
        return user;
    }
}
