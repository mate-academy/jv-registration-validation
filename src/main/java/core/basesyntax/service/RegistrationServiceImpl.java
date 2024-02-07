package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ExpectedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private static final int MIN_LOGIN_CHARACTERS = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateCredential(user);
        return storageDao.add(user);
    }

    private void validateCredential(User user) {

        if (user == null) {
            throw new ExpectedException("User can't be null");
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new ExpectedException("Not valid age: "
                    + ". Min allowed age is " + MIN_AGE);
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_CHARACTERS) {
            throw new ExpectedException("Not valid login: "
                    + ". Min allowed login length is " + MIN_LOGIN_CHARACTERS);
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new ExpectedException("Not valid password: "
                    + ". Min allowed password length is " + MIN_PASSWORD_CHARACTERS);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new ExpectedException("User " + user.getLogin() + " exist");
        }
    }
}
