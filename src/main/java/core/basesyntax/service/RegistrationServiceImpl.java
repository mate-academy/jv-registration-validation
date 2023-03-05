package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidRegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_UPPER_BOUND = 10;
    private static final int PASSWORD_UPPER_BOUND = 16;
    private static final int PASSWORD_LOWER_BOUND = 6;
    private static final int AGE_UPPER_BOUND = 130;
    private static final int AGE_LOWER_BOUND = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("Null user value");
        }
        if (storageDao.get(user.getLogin()) == null) {
            isValidData(user);
            return storageDao.add(user);
        }
        throw new InvalidRegistrationDataException(
                "Can't register a user with existing login " + user.getLogin());
    }

    private void isValidData(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (login == null || password == null || age == null) {
            throw new InvalidRegistrationDataException("User's information can't be null");
        }
        if (login.isEmpty()) {
            throw new InvalidRegistrationDataException("Login can't be empty");
        }
        if (login.length() > LOGIN_UPPER_BOUND) {
            throw new InvalidRegistrationDataException("Login can't be more than 10 symbols");
        }
        if (login.contains(" ")) {
            throw new InvalidRegistrationDataException("Login can't have spaces");
        }

        if (age < AGE_LOWER_BOUND || age > AGE_UPPER_BOUND) {
            throw new InvalidRegistrationDataException("Not valid age");
        }

        if (password.length() < PASSWORD_LOWER_BOUND
                || password.length() > PASSWORD_UPPER_BOUND) {
            throw new InvalidRegistrationDataException(
                    "Password should be more than 5 and less than 17 symbols");
        }
    }
}
