package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.UserExistException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null value");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("User login should be at least"
                    + MIN_LOGIN_LENGTH
                    + "characters long"
            );
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("User password should be at least"
                    + MIN_PASSWORD_LENGTH
                    + "characters long"
            );
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User must be at least"
                    + MIN_AGE
                    + "years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserExistException("This user already exist");
        }

        storageDao.add(user);

        return user;
    }
}
