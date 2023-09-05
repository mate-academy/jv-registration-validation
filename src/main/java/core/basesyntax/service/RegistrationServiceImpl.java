package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserInvalidDataException {
        if (Objects.isNull(user)) {
            throw new UserInvalidDataException("User data can't be null");
        }
        if (user.getLogin() == null) {
            throw new UserInvalidDataException("User login can't be null");
        }
        if (user.getAge() == null) {
            throw new UserInvalidDataException("User age can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserInvalidDataException("User password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserInvalidDataException("User with this login already exist "
                    + user.getLogin());
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserInvalidDataException("User password length must be at least 6 symbols"
                    + ", but length was:  " + user.getPassword().length());
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new UserInvalidDataException("User login length must be at least 6 symbols,"
                    + " but length was:  " + user.getLogin().length());
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserInvalidDataException("User age must be at least 18, but was: "
                    + user.getAge());
        }
        return storageDao.add(user);
    }
}
