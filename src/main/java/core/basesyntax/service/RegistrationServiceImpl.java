package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ExpectedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_PASS_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ExpectedException {
        if (user == null) {
            throw new ExpectedException("Argument cannot be null!");
        }
        if (user.getAge() == null) {
            throw new ExpectedException("User's age is cannot be null!");
        }
        if (user.getPassword() == null) {
            throw new ExpectedException("Password cannot be null");
        }
        if (user.getPassword().trim().equals("")) {
            throw new ExpectedException("Login cannot contain only whitespace");
        }
        if (user.getPassword().length() < MINIMAL_PASS_LENGTH) {
            throw new ExpectedException("Password must be longer than " + MINIMAL_PASS_LENGTH);
        }
        if (user.getLogin() == null) {
            throw new ExpectedException("Login cannot be null");
        }
        if (user.getLogin().trim().equals("")) {
            throw new ExpectedException("Login cannot contain only whitespace");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new ExpectedException("Login must be longer than " + MINIMAL_LOGIN_LENGTH);
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new ExpectedException("Age should be bigger than " + MINIMAL_AGE);
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        return null;
    }
}
