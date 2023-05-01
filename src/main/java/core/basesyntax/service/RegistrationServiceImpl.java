package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AMOUNT_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegisterException(" user can't be null");
        }
        checkValidLogin(user);
        checkValidPassword(user);
        checkValidAge(user);
        return storageDao.add(user);
    }

    private void checkValidLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegisterException(user.getLogin() + " login can't be null");
        }
        if (user.getLogin().length() < MIN_AMOUNT_CHARACTERS) {
            throw new RegisterException(user.getLogin() + " login must be more than"
            + MIN_AMOUNT_CHARACTERS + " characters");
        }
        if (user.getLogin().equals(storageDao.get(user.getLogin()))) {
            throw new RegisterException(user.getLogin() + " with the same login already exists");
        }
    }

    private void checkValidPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegisterException(user.getLogin() + " password can't be null");
        }
        if (user.getPassword().length() < MIN_AMOUNT_CHARACTERS) {
            throw new RegisterException(user.getLogin() + " password must be more than "
            + MIN_AMOUNT_CHARACTERS + " character");
        }
    }

    private void checkValidAge(User user) {
        if (user.getAge() == null) {
            throw new RegisterException(user.getLogin() + " user age can't be null");
        }
        if (user.getAge() < 18) {
            throw new RegisterException(user.getLogin() + " age should be bigger then "
            + MIN_AGE);
        }
    }
}
