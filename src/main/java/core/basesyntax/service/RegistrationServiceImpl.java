package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationServiceException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_REGISTRATION_AGE = 18;
    private static final int MAX_REGISTRATION_AGE = 100;
    private static final int MIN_LENGTH_OF_LOGIN = 6;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int LOGIN_START_INDEX = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationServiceException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationServiceException("User login does not be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationServiceException("User password does not be null or empty");
        }
        if (user.getAge() == null
                || user.getAge() < MIN_REGISTRATION_AGE
                || user.getAge() > MAX_REGISTRATION_AGE) {
            throw new RegistrationServiceException("User age does not be null or less :"
                    + MIN_REGISTRATION_AGE
                    + "and more: "
                    + MAX_REGISTRATION_AGE
                    + " years old");
        }
        if (!Character.isLetter(user.getLogin().charAt(LOGIN_START_INDEX))) {
            throw new RegistrationServiceException("User login: "
                    + user.getLogin()
                    + " does not start with symbol");
        }
        if (user.getLogin().length() < MIN_LENGTH_OF_LOGIN) {
            throw new RegistrationServiceException("User login :"
                    + user.getLogin()
                    + " must be have no less "
                    + MIN_LENGTH_OF_LOGIN
                    + " symbols");
        }
        if (user.getPassword().length() < MIN_LENGTH_OF_PASSWORD) {
            throw new RegistrationServiceException("User password :"
                    + user.getPassword()
                    + " must be have no less "
                    + MIN_LENGTH_OF_PASSWORD
                    + " symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User with this login: "
                    + user.getLogin()
                    + " already exists");
        }
        return storageDao.add(user);
    }
}
