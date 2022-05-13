package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int Age_18 = 18;
    private static final int SIX_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid input user");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid login");
        }
        String pureLogin = user.getLogin().trim();
        user.setLogin(pureLogin);
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Empty login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exists");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Invalid age");
        }
        if (user.getAge() < Age_18) {
            throw new RuntimeException("The age should be at least 18 years old");
        }

        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid password");
        }
        String purePassword = user.getPassword().trim();
        user.setPassword(purePassword);
        if (user.getPassword().length() < SIX_SYMBOLS) {
            throw new RuntimeException("The password should be at least 6 symbols");
        }
        storageDao.add(user);
        return user;
    }
}

