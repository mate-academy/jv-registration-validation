package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGT_PATHWORD = 6;
    public static final int MIN_AGE_USER = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException();
        }

        if (checkLogin(user) && checkAge(user) && checkPassword(user)) {
            return storageDao.add(user);
        }
        return null;
    }

    private boolean checkLogin(User user) {
        for (User userInStorage : Storage.people) {
            if (userInStorage.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAge(User user) {
        return user.getAge() >= MIN_AGE_USER;
    }

    private boolean checkPassword(User user) {
        return user.getPassword().length() >= MIN_LENGT_PATHWORD;
    }
}
