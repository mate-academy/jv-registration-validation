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
        workWithNull(user);
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("you are not of legal age");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password length shod be more or equals: "
                    + MIN_PASSWORD_LENGTH);
        }
        return storageDao.add(user);
    }

    public void workWithNull(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be: " + user);
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login can not be: " + user.getLogin());
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can not be: " + user.getAge());
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password can not be: " + user.getPassword());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException(String.format("User with login: %s already created",
                    user.getLogin()));
        }
    }
}
