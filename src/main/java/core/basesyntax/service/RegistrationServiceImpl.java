package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null
                && user.getLogin() != null
                && user.getLogin().length() > 0
                && user.getLogin().matches("\\w{6,18}")
                && !user.getLogin().equals(user.getPassword())
                && user.getPassword() != null
                && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_PASS_LENGTH) {
            return storageDao.add(user);
        }
        throw new RuntimeException("Not valid user!");
    }
}
