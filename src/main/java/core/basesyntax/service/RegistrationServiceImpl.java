package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Data can not be null");
        }
        if (!(Storage.people.contains(user)) && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_LENGTH) {
            storageDao.add(user);
        } else {
            throw new RuntimeException("Invalid data");
        }
        return user;
    }
}
