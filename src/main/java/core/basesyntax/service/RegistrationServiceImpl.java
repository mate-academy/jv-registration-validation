package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            if (user.getLogin() != null && user.getLogin().length() > 0
                    && user.getAge() != null && user.getPassword() != null) {
                User foundUser = storageDao.get(user.getLogin());
                if (foundUser == null && user.getAge() >= MIN_AGE
                        && user.getPassword().length() >= MIN_PASSWORD_LENGTH) {
                    storageDao.add(user);
                    return user;
                }
            }
        }
        throw new RuntimeException("User's data is wrong");
    }
}
