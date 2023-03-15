package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;

    @Override
    public User register(User user)throws InvalidUserDataException {
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || login.isEmpty() || password == null
                || password.length() < MIN_PASSWORD_CHARACTERS
                || age == null || age < MIN_USER_AGE) {
            throw new InvalidUserDataException("Invalid user data");
        }

        if (storageDao.get(login) != null) {
            throw new InvalidUserDataException("User with this login already exists");
        }
        User result = storageDao.add(user);
        return result;
    }
}
