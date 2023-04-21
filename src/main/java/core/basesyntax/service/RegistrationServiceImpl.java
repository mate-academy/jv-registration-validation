package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int CHARACTERS_LIMIT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("There is a user with "
                    + "such login in the Storage already");
        }
        if (user.getLogin().length() < CHARACTERS_LIMIT) {
            throw new RegistrationServiceException("Your login should have at least "
                    + CHARACTERS_LIMIT + " characters");
        }
        if (user.getPassword().length() < CHARACTERS_LIMIT) {
            throw new RegistrationServiceException("Your password should have at least "
                    + CHARACTERS_LIMIT + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("Your age should be "
                    + MIN_AGE + "+ to pass th registration");
        }
        return storageDao.add(user);
    }
}
