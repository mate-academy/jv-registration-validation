package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.jetbrains.annotations.NotNull;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserExist(user);
        checkUser(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkUserExist(@NotNull User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Cannot register new user. User"
                    + user.getLogin() + " already exists.");
        }
    }

    private void checkLogin(@NotNull User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Cannot register user. Login is null.");
        }
    }

    private void checkAge(@NotNull User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("Cannot register user. "
                    + "Age should be 18 and more.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Cannot register user. "
                    + "Age is null.");
        }
    }

    private void checkPassword(@NotNull User user) {
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Cannot register user. "
                    + "Password should be at least 6 symbols.");
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Cannot register user. "
                    + "User is null.");
        }
    }
}
