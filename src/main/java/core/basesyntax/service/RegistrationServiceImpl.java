package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHAR_FOR_PASS = 6;
    private static final int MIN_AGE_FOR_REG = 18;
    StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (userNullCheck(user)
                && loginUserCheck(user)
                && userAgeCheck(user)
                && userPasswordCheck(user)) {
                storageDao.add(user);
                return user;
        }
        return null;
    }

    private boolean loginUserCheck(User user) {
       return storageDao.get(user.getLogin()) == null;
    }

    private boolean userNullCheck(User user) {
        return user != null;
    }

    private boolean userAgeCheck(User user) {
        return user.getAge() >= MIN_AGE_FOR_REG;
    }

    private boolean userPasswordCheck(User user) {
        return user.getPassword().length() >= MIN_CHAR_FOR_PASS;
    }
}

