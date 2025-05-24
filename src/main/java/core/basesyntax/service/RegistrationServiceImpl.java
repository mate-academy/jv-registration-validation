package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null && user.getLogin() != null && user.getPassword() != null
                && user.getAge() != null && storageDao.get(user.getLogin()) == null
                && user.getLogin().length() > 5 && user.getPassword().length() > 5
                && user.getAge() > 17 && storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        throw new CustomException("Invalid data");
    }
}
