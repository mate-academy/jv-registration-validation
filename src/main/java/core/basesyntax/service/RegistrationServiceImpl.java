package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private final int MIN_VALID_AGE = 18;
    private final int LOGIN_LENGTH = 6;
    private final int PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user.getLogin() == null || user.getLogin().length() < LOGIN_LENGTH) {
            throw new RegistrationException("The login can not be null " +
                    "and must be no less than 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RegistrationException("The password can't be null and " +
                    "must contain at least 6 characters");
        }

        if (user.getAge() < MIN_VALID_AGE) {
            throw new RegistrationException("The allowed age must be 18 " +
                    "years old");
        }


        User existingUser = storageDao.get(user.getLogin());

        if (existingUser != null) {
            try {
                throw new RegistrationException("the user with the provided login" +
                        " exist already");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }

        return storageDao.add(user);
    }
}
