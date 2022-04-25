package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        int ageLimit = 18;
        int minLengthPassword = 6;
        if (user == null || user.getAge() == null || user.getLogin() == null
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
