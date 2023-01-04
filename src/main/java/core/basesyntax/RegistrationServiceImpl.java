package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao users = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User can't be null");
        }
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        return users.add(user);
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationServiceException("Password must contain at least "
                    + MINIMUM_PASSWORD_LENGTH + " symbols");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Age can't be null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationServiceException("Password must contain at least "
                    + MINIMUM_AGE + " symbols");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login can't be null");
        }
        if (users.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("This login is already exists");
        }

    }
}
