package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.MyException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_AND_LOGIN_INDEX = 6;
    private static final int MIN_AGE_INDEX = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws MyException {
        User checkedUsersLoginInStorage = storageDao.get(user.getLogin());
        if (user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new MyException("Invalid data!");
        }

        if (checkedUsersLoginInStorage != null
                || user.getLogin().length() < MIN_PASSWORD_AND_LOGIN_INDEX
                || user.getPassword().length() < MIN_PASSWORD_AND_LOGIN_INDEX
                || user.getAge() < MIN_AGE_INDEX) {
            throw new MyException("Invalid data!");
        }
        storageDao.add(user);
        return user;
    }
}
