package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.DuplicateUserException;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("Invalid user implementation, user == null");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || password == null || age == null) {
            throw new InvalidUserException("Invalid data:"
                    + " login,data,age can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH
                || password.length() < MIN_PASSWORD_LENGTH || age < MIN_USER_AGE) {
            throw new InvalidUserException("Invalid user data: "
                    + "password and login must include at least six characters "
                    + "age must be at least 18");
        }
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new DuplicateUserException("User with the same login already exists");
        }

        return storageDao.add(user);
    }
}
