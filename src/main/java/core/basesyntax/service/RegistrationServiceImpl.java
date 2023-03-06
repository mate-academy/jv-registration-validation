package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int VALID_LENGTH_PASS = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User object can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("User age can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("User password can't be null");
        }
        StorageDaoImpl storageDao = new StorageDaoImpl();
        if (storageDao.get(user.getLogin()) == null) {
            if (isValidAge(user) && isValidPassword(user)) {
                storageDao.add(user);
                return user;
            }
        }
        return null;
    }

    private boolean isValidAge(User user) {
        return user.getAge() >= VALID_AGE;
    }

    private boolean isValidPassword(User user) {
        return user.getPassword().length() >= VALID_LENGTH_PASS;
    }
}
