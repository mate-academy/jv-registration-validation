package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null
                || Storage.people.contains(storageDao.get(user.getLogin()))
                || user.getAge() < MIN_AGE
                || user.getAge() > MAX_AGE
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException();
        }
        storageDao.add(user);
        return user;
    }
}
