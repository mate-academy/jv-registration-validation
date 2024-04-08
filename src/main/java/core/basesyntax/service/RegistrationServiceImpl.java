package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException(ErrorMessage.ERROR_NULL_USER);
        }

        if (user.getId() == null) {
            throw new ValidationException(ErrorMessage.ERROR_ID_REQUIRED);
        }

        if (user.getLogin() == null) {
            throw new ValidationException(ErrorMessage.ERROR_LOGIN_REQUIRED);
        }

        if (user.getPassword() == null) {
            throw new ValidationException(ErrorMessage.ERROR_PASSWORD_REQUIRED);
        }

        if (user.getAge() == null) {
            throw new ValidationException(ErrorMessage.ERROR_AGE_REQUIRED);
        }

        if (storageDao.get(user.getLogin()) == user) {
            throw new ValidationException(ErrorMessage.ERROR_USER_EXISTS);
        }

        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new ValidationException(ErrorMessage.ERROR_SHORT_LOGIN);
        }

        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new ValidationException(ErrorMessage.ERROR_SHORT_PASSWORD);
        }

        if (user.getAge() < USER_MIN_AGE) {
            throw new ValidationException(ErrorMessage.ERROR_UNDERAGE_USER);
        }

        if (user.getLogin().contains(" ")) {
            throw new ValidationException(ErrorMessage.ERROR_SPACE_IN_LOGIN);
        }

        storageDao.add(user);
        return user;
    }
}
