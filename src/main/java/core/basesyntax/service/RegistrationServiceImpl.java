package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("No one to register");
        }
        checkAge(user.getAge());
        checkCredentials(user.getLogin(), user.getPassword());
        return storageDao.add(user);
    }

    private void checkAge(int age) {
        if (age < 18) {
            throw new RuntimeException("You are too young");
        }
        if (age > 122) {
            throw new RuntimeException("Don't pass fake age");
        }
    }

    private void checkCredentials(String login, String password) {
        if (login != null && storageDao.get(login) == null) {
            checkPassword(password);
        } else {
            throw new RuntimeException("Incorrect login");
        }
    }

    private void checkPassword(String password) {
        if (password != null && password.length() >= 6) {
            return;
        } else {
            throw new RuntimeException("password is too bad");
        }
    }
}
