package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            return null;
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You are too young to register");
        }
        return storageDao.add(user);


    }
}


