package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserInvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        dataVerification(user);
        storageDao.add(user);
        return user;
    }

    private void dataVerification(User user) {
        if (user == null) {
            throw new UserInvalidDataException(
                    "You cannot pass a null value to a method");
        }
        if (user.getLogin() == null) {
            throw new UserInvalidDataException(
                    "User login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new UserInvalidDataException(
                    "User password cannot be null");
        }
        if (user.getAge() <= 0) {
            throw new UserInvalidDataException(
                    "User age cannot be less than 0 or equal to 0. "
                            + "Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserInvalidDataException(
                    "User login must be at least 6 characters long. Actual login length "
                            + user.getLogin().length());
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserInvalidDataException(
                    "User password must be at least 6 characters long. Actual password length "
                            + user.getPassword().length());
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserInvalidDataException(
                    "User must be over 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidDataException(
                    "User already present in database");
        }
    }
}
