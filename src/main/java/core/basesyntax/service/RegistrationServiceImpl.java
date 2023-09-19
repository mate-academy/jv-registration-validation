package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String WRONG_PASSWORD = "The number of characters"
            + " in the password is less than required";
    private static final String WRONG_LOGIN = "The number of characters in the"
            + " login is less than required";
    private static final String USER_ALREADY_EXIST = "User already exist.";
    private static final Integer MIN_USER_AGE = 18;
    private static final String USER_AGE_TOO_LOW = "User age must be greater"
            + " than or equal to 18";
    private static final int MIN_DATA_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_DATA_LENGTH) {
            throw new InvalidUserDataException(WRONG_LOGIN);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_DATA_LENGTH) {
            throw new InvalidUserDataException(WRONG_PASSWORD);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(USER_ALREADY_EXIST);
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new InvalidUserDataException(USER_AGE_TOO_LOW);
        }

        return storageDao.add(user);
    }

}
