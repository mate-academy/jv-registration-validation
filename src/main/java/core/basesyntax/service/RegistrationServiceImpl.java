package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can`t be null");
        }
        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Not valid login: "
                    + user.getLogin() + ". Min allowed login length is: "
                    + MIN_CHARACTERS);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new RegistrationException("Not valid password: "
                    + user.getPassword() + ". Min allowed password length is: "
                    + MIN_CHARACTERS);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can`t be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is: "
                    + MIN_AGE);
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Age have to be a positive number");
        }
        return storageDao.add(user);
    }
}
