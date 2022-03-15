package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() >= MIN_AGE
                && user.getPassword() != null
                && user.getPassword().length() >= MIN_LENGTH_PASSWORD
                && storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RuntimeException("The entered username "
                + "or password does not meet the requirements");
    }
}
