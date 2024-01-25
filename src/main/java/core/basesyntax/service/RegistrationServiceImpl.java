package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.MyException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        User checkedUsersLoginInStorage = storageDao.get(user.getLogin());
        if (user.getLogin().length() < 6 || checkedUsersLoginInStorage != null
                || user.getPassword().length() < 6 || user.getAge() < 18) {
            throw new MyException("Invalid data!");
        }
        storageDao.add(user);
        return user;
    }
}
