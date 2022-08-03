package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user == null || user.getLogin() == null
                || user.getAge() == null || user.getPassword() == null || user.getAge() < 0) {
            throw new RegistrationServiceException("Invalid data!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("Login already exist!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("User age must be not less "
                    + MIN_LENGTH_PASSWORD);
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationServiceException("Minimal length of the password must be not less"
                    + MIN_LENGTH_PASSWORD);
        }
        return storageDao.add(user);
    }
}
