package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static StorageDaoImpl storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getId() == null
                && user.getLogin() != null
                && user.getPassword() != null
                && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_PASSWORD_LENGTH
                && storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Invalid data");
    }
}
