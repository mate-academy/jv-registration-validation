package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CustomException("user can not be null");
        }

        if (user.getAge() == null) {
            throw new CustomException("age can not be null");
        }

        if (user.getLogin() == null) {
            throw new CustomException("login can not be null");
        }

        if (user.getPassword() == null) {
            throw new CustomException("password can not be null");

        }

        if (user.getAge() < MIN_AGE) {
            throw new CustomException("age is less then expected");
        }

        if (user.getLogin().length() < 6) {
            throw new CustomException("login is less then expected");
        }

        if (user.getPassword().length() < 6) {
            throw new CustomException("password is less then expected");

        }

        User existing = storageDao.get(user.getLogin());

        if (existing != null) {
            throw new CustomException("user already exist");
        }

        return storageDao.add(user);
    }
}
