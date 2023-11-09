package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_SIZE = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }

        if (isNotValidLogin(user) || isNotValidPassword(user) || isAgeLeast(user)) {
            throw new InvalidDataUserException("Data of user not valid");
        }

        if (isExistUserStorage(user)) {
            throw new RuntimeException("User already exist");
        }

        return storageDao.add(user);
    }

    private boolean isExistUserStorage(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean isAgeLeast(User user) {
        return user.getAge() < VALID_AGE;
    }

    private boolean isNotValidPassword(User user) {
        return user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getPassword().length() < VALID_SIZE;
    }

    private boolean isNotValidLogin(User user) {
        return user.getLogin() == null
                || user.getLogin().isEmpty()
                || user.getLogin().length() < VALID_SIZE;
    }

}
