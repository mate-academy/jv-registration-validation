package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null
                || user.getPassword() == null
                || user.getLogin() == null
                || user.getId() == null) {
            throw new InvalidDataException();
        }
        if (user.getLogin().length() >= MIN_LENGTH
                && user.getPassword().length() >= MIN_LENGTH
                && user.getAge() >= MIN_AGE
                && storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        throw new InvalidDataException();
    }
}
