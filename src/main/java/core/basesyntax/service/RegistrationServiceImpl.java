package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_ALLOWED_AGE = 18;
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getLogin().contains(" ")
                || user.getLogin().equals("")
                || user.getPassword() == null
                || user.getPassword().contains(" ")
                || user.getAge() == null
                || user.getAge() < MINIMUM_ALLOWED_AGE
                || user.getPassword().length() < PASSWORD_MINIMUM_LENGTH
                || !Objects.equals(storageDao.get(user.getLogin()), null)) {
            throw new RuntimeException("Input data is not valid");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
