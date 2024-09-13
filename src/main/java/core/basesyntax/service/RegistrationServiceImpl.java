package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationFailedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ACCEPTABLE_AGE = 18;
    private static final int VALID_LENGTH = 6;
    private static final String UPPER_CASE_SYMBOLS = ".*[A-Z].*";
    private static final String NULL_ERROR_MESSAGE = "can't be null ";
    private static final String MIN_LENGTH_ERROR_MESSAGE = "must contain 6 characters or more ";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationFailedException("Login " + NULL_ERROR_MESSAGE + user.getLogin());
        }
        if (user.getPassword() == null) {
            throw new RegistrationFailedException("Password " + NULL_ERROR_MESSAGE + user.getPassword());
        }
        if (user.getAge() == null) {
            throw new RegistrationFailedException("Age " + NULL_ERROR_MESSAGE + user.getAge());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailedException("Login is already exist " + user.getLogin());
        }
        if (user.getLogin().length() < VALID_LENGTH) {
            throw new RegistrationFailedException("Login " + MIN_LENGTH_ERROR_MESSAGE + user.getLogin());
        }
        if (user.getPassword().length() < VALID_LENGTH) {
            throw new RegistrationFailedException("Password " + MIN_LENGTH_ERROR_MESSAGE + user.getLogin());
        }
        if (user.getLogin().matches(UPPER_CASE_SYMBOLS)) {
            throw new RegistrationFailedException("Enter the login in lower case " + user.getLogin());
        }
        if (user.getAge() < ACCEPTABLE_AGE) {
            throw new RegistrationFailedException("You are not of legal age " + user.getAge());
        }
        return storageDao.add(user);
    }
}
