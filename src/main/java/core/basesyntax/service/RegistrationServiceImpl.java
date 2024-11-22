package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new InvalidRegistrationException("User's login can't be null");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidRegistrationException("User's login"
                    + " can't have less than 6 character's");
        }

        if (user.getPassword() == null) {
            throw new InvalidRegistrationException("User's password can't be null");
        }

        if (user.getAge() == null) {
            throw new InvalidRegistrationException("User's age can't be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidRegistrationException("User's password can't "
                    + "be less than 6 character's");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidRegistrationException("User can't be less than 18");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationException("User: " + user.getLogin()
                    + "already exist");
        }
        return storageDao.add(user);
    }
}