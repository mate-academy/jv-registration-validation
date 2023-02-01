package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exceptions.PasswordException;
import core.basesyntax.service.exceptions.UserRegistrationException;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidate(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("This user with login already exists");
        }
        return storageDao.add(user);
    }

    private void userValidate(User user) {
        if (user == null) {
            throw new UserRegistrationException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login must be filled");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age must be filled");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new UserRegistrationException("the user is too young. His age should be "
                    + MIN_USER_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordException("Password length is incorrect."
                    + " The length of the password is correct " + MIN_PASSWORD_LENGTH);
        }
    }
}
