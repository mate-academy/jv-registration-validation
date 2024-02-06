package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ExpectedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkWrongValue(user);
        return storageDao.add(user);
    }

    private void checkWrongValue(User user) {

        if (user == null) {
            throw new ExpectedException("User can't be null");
        }

        if (user.getPassword() == null) {
            throw new ExpectedException("Password can't be null");
        }

        if (user.getLogin() == null) {
            throw new ExpectedException("Login can't be null");
        }

        if (user.getAge() == null) {
            throw new ExpectedException("Age can't be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new ExpectedException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        if (user.getLogin().length() < MIN_CHARACTERS) {
            throw new ExpectedException("Not valid login: "
                    + user.getLogin() + ". Min allowed login length is " + MIN_CHARACTERS);
        }

        if (user.getPassword().length() < MIN_CHARACTERS) {
            throw new ExpectedException("Not valid password: "
                    + user.getPassword() + ". Min allowed password length is " + MIN_CHARACTERS);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new ExpectedException("User " + user.getLogin() + " exist");
        }
    }
}
