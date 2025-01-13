package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidCredentialsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_AND_PASSWORD_LEN = 6;
    private static final int REQUIRED_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidCredentialsException("User cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidCredentialsException(
                    "Sorry but login: " + user.getLogin() + " is taken"
            );
        }
        if (user.getLogin() == null || user.getLogin().length() < LOGIN_AND_PASSWORD_LEN) {
            throw new InvalidCredentialsException(
                    "Sorry but your login must be " + LOGIN_AND_PASSWORD_LEN + " characters or more"
            );
        }
        if (user.getPassword() == null || user.getPassword().length() < LOGIN_AND_PASSWORD_LEN) {
            throw new InvalidCredentialsException(
                    "Sorry but your password must be "
                            + LOGIN_AND_PASSWORD_LEN + " characters or more"
            );
        }
        if (user.getAge() == null || user.getAge() < REQUIRED_AGE) {
            throw new InvalidCredentialsException(
                    "Sorry but you must be " + REQUIRED_AGE + " years old or older"
            );
        }
        return storageDao.add(user);
    }
}
