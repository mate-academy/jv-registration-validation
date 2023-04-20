package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
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

    boolean checkUserLoginForIdentity(User user) {
        return storageDao.get(user.getLogin()) == null;
    }

    private boolean checkUserLoginForLength(User user) {
        return user.getLogin().length() >= 6;
    }

    private boolean checkUserPasswordForLength(User user) {
        return user.getPassword().length() >= 6;
    }

    private boolean checkUserForAge(User user) {
        return user.getAge() >= 18;
    }

}
