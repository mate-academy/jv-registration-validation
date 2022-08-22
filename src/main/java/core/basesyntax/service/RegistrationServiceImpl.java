package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 0 || user == null || user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RegistrationServiceException("Invalid data? how? why? eh-hhh....");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("A few years missing! Come next year!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("We already have your namesake!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationServiceException(
                    "A good password should be longer than 6 characters!");
        }
        return storageDao.add(user);
    }
}
