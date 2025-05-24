package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CustomException("User must not be null");
        }
        if (user.getLogin() == null) {
            throw new CustomException("Login must not be null");
        }
        if (user.getPassword() == null) {
            throw new CustomException("Password must not be null");
        }
        if (user.getAge() == null) {
            throw new CustomException("Age must not be null");
        }
        if (user.getLogin().length() <= 5) {
            throw new CustomException("Login must be longer than 5 characters");
        }
        if (user.getPassword().length() <= 5) {
            throw new CustomException("Password must be longer than 5 characters");
        }
        if (user.getAge() <= 17) {
            throw new CustomException("User must be older than 17");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomException("User with this login already exists");
        }

        storageDao.add(user);
        return user;
    }
}
