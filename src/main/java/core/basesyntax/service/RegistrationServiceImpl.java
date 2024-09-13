package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationFailedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ACCEPTABLE_AGE = 18;
    private static final String UPPER_CASE_SYMBOLS = ".*[A-Z].*";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationFailedException("Login can't be null" + user.getLogin());
        }
        if (user.getPassword() == null) {
            throw new RegistrationFailedException("Password can't be null" + user.getPassword());
        }
        if (user.getAge() == null) {
            throw new RegistrationFailedException("Age can't be null" + user.getAge());
        }
        if (storageDao.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new RegistrationFailedException("Login is already exist " + user.getLogin());
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
