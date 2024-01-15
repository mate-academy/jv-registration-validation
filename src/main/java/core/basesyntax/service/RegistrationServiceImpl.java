package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.DataCorrectnessException;
import core.basesyntax.exceptions.LoginDuplicateException;
import core.basesyntax.exceptions.NullUserDataException;
import core.basesyntax.exceptions.NullUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOWABLE_LOGIN_SIZE = 6;
    private static final int ALLOWABLE_PASSWORD_SIZE = 6;
    private static final int AGE_RESTRICTION = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullUserException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new NullUserDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new NullUserDataException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new NullUserDataException("Age can't be null");
        }
        if (user.getLogin().length() < ALLOWABLE_LOGIN_SIZE) {
            throw new DataCorrectnessException("Invalid login size."
                    + " Size " + ALLOWABLE_LOGIN_SIZE + " expected");
        }
        if (user.getPassword().length() < ALLOWABLE_PASSWORD_SIZE) {
            throw new DataCorrectnessException("Invalid password size."
                    + " Size " + ALLOWABLE_PASSWORD_SIZE + " expected");
        }
        if (user.getAge() < AGE_RESTRICTION) {
            throw new DataCorrectnessException("Not valid age: "
                    + user.getAge() + ". Min allowed age is "
                    + AGE_RESTRICTION);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginDuplicateException("Login "
                    + user.getLogin() + " already exists");
        }

        return storageDao.add(user);
    }
}
