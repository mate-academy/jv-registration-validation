package core.basesyntax.service;

import core.basesyntax.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        valiidateUser(user);
        return storageDao.add(user);
    }

    private void valiidateUser(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserException("Invalid login: " + user.getLogin());
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserException("Invalid password: " + user.getPassword());
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException("Invalid age: " + user.getAge());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with login already exists" + user.getLogin());
        }
    }
}
