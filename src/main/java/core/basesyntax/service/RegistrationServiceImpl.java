package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSSWORD = 8;
    private static final int MIN_LENGTH_LOGIN = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_PASSSWORD) {
            throw new RegistrationException("User's password cannot be null or less than "
                    + MIN_LENGTH_PASSSWORD);
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new RegistrationException("User's login cannot be null or less than "
                    + MIN_LENGTH_LOGIN);
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age cannot be null or less than "
                    + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already taken");
        }

        return storageDao.add(user);
    }
}
