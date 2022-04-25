package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ageLimit = 18;
    private static final int minLengthPassword = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        if (user.getAge() == null || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("You must not leave any fields empty");
        }
        if (user.getAge() < ageLimit) {
            throw new RuntimeException("Your age must be greater than eighteen");
        }
        if (user.getPassword().length() < minLengthPassword) {
            throw new RuntimeException("Your password must have more than 6 characters");
        }
        return storageDao.add(user);
    }
}
