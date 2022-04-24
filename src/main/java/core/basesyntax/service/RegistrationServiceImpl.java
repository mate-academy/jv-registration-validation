package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.jetbrains.annotations.NotNull;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(@NotNull User user) {
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            // throw new RuntimeException("You must not leave any fields empty");
            throw new NullPointerException();
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Your age must be greater than eighteen");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Your password must have more than 6 characters");
        }

        return storageDao.add(user);
    }
}
