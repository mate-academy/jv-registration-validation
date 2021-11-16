package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE_EXPECTANCY = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null
                || user.getLogin().isEmpty()) {
            throw new RuntimeException("User login already exist or input data is null");
        }
        if (user.getAge() < MIN_AGE_EXPECTANCY || user.getAge() == null) {
            throw new RuntimeException("User`s age should be at least 18 y.o.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH || user.getPassword() == null) {
            throw new RuntimeException("User`s password is invalid");
        }
        return storageDao.add(user);
    }
}
