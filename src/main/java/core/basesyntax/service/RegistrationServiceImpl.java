package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getId() == null || user.getAge() == null
                         || user.getPassword() == null || user.getLogin() == null
                         || user.getPassword().isEmpty() || user.getLogin().isEmpty()
                         || user.getAge() < MIN_AGE
                         || user.getPassword().length() < MIN_PASSWORD_LENGTH
                         || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Wrong input");
        }
        return storageDao.add(user);
    }
}
