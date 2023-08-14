package core.basesyntax.model;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.service.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {
    static final int AGE_PERMISION = 18;
    static final int MIN_PASSWORD_AND_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_AND_LOGIN_LENGTH) {
            throw new RegistrationException("Password length less then "
                    + MIN_PASSWORD_AND_LOGIN_LENGTH + " characters");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_PASSWORD_AND_LOGIN_LENGTH) {
            throw new RegistrationException("Login length less then "
                    + MIN_PASSWORD_AND_LOGIN_LENGTH + " characters");
        }
        if (user.getLogin().equals(storageDao.get(user.getLogin()))) {
            throw new RegistrationException("This user exist in storage, please change your "
                    + "login");
        }
        if (user.getAge() < AGE_PERMISION || user.getAge() == null) {
            throw new RegistrationException("Person with your age doesn't allow to registration "
                    + "in this servise");
        }
        if (!user.getPassword().matches(".*[A-Z].*") && !user.getPassword()
                .matches(".*[a-z].*")) {
            throw new RegistrationException("Password need to contain at least one character "
                    + "in UpperCaseletter and LowerCase");
        }
        storageDao.add(user);
        return user;
    }
}

