package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new RegistrationException("The user is existed. Enter a valid login");
        }

        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new RegistrationException("Enter valid password");
        }

        if (user.getAge() == null || user.getAge().equals("")) {
            throw new RegistrationException("Enter valid age");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login cant be less then 6 characters");
        }

        if (user.getPassword().length() < MAX_PASSWORD_LENGTH) {
            throw new RegistrationException("The password can't be less then 6 characters");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Your age can not be les then "
                    + MIN_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login already exists");
        }

        storageDao.add(user);
        return user;
    }
}
