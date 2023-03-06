package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User need to have age - 18 or more. The age -"
                    + user.getAge() + "is invalid");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's password" + user.getPassword()
                    + "should to contain 6 symbols or more");
        }
        if (storageDao.get(user.getLogin()) != null && storageDao.get(user.getLogin())
                .getLogin().equals(user.getLogin())) {
            throw new RegistrationException("User with such login" + user.getLogin()
                    + "already exist");
        }
        return storageDao.add(user);
    }
}
