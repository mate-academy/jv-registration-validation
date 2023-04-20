package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user == null) {
            throw new InvalidUserException();
        }
        boolean correctUser = checkUserForAge(user)
                && checkUserLoginForIdentity(user)
                && checkUserLoginForLength(user)
                && checkUserPasswordForLength(user);
        if (correctUser) {
            storageDao.add(user);
            return user;
        }
        throw new InvalidUserException();
    }

    public boolean checkLogin(User user) {
        return checkUserLoginForIdentity(user);
    }

    private boolean checkUserLoginForIdentity(User user) {
        if (user.getLogin() == null) {
            return false;
        }
        return storageDao.get(user.getLogin()) == null;
    }

    private boolean checkUserLoginForLength(User user) {
        return user.getLogin().length() >= MINIMAL_LENGTH;
    }

    private boolean checkUserPasswordForLength(User user) {
        if (user.getPassword() == null) {
            return false;
        }
        return user.getPassword().length() >= MINIMAL_LENGTH;
    }

    private boolean checkUserForAge(User user) {
        if (user.getAge() == null) {
            return false;
        }
        return user.getAge() >= MINIMAL_AGE;
    }
}
