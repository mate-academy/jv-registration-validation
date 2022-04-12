package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can`t be null");
        }
        if (user.getLogin().isEmpty() || user.getLogin().length() <= 2) {
            throw new RuntimeException("Login can`t be empty or length over 2");
        }
        if (user.getPassword().isEmpty() || user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be over 5 symbols");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Age must be over behind 17");
        }
        storageDao.add(user);
        return user;
    }
}
