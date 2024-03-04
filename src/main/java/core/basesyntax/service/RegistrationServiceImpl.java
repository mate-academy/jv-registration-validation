package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SIZE = 6;
    private static final int MIN_AGE = 18;
    private static final String REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\."
            + "[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("The user is null");
        }
        String login = user.getLogin();
        if (login == null || login.length() < MIN_SIZE) {
            throw new UserValidationException("Not valid login: " + login
                    + ". Min allowed login is " + MIN_SIZE);
        }
        String password = user.getPassword();
        if (password == null || password.length() < MIN_SIZE) {
            throw new UserValidationException("Not valid password: " + password
                    + ". Min allowed password is " + MIN_SIZE);
        }
        Integer age = user.getAge();
        if (age == null || age < MIN_AGE) {
            throw new UserValidationException("Not valid age: " + age
                    + ". Min allowed age is " + MIN_SIZE);
        }
        if (Pattern.compile(REGEX_PATTERN).matcher(user.getLogin()).matches()) {
            User checkedUser = storageDao.get(user.getLogin());
            if (checkedUser != null) {
                return checkedUser;
            }
            return storageDao.add(user);
        }
        throw new UserValidationException("The login is uncorrected");
    }
}
