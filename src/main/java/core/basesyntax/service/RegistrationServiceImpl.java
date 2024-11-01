package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            if (user.getLogin() != null && user.getLogin().length() >= 6) {
                if (user.getPassword() != null && user.getPassword().length() >= 6) {
                    if (user.getAge() != null && user.getAge() >= 18) {
                        if (storageDao.get(user.getLogin()) == null) {
                            return storageDao.add(user);
                        }
                    }
                }
            }
        }
        throw new UserRegistrationException();
    }
}
