package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserIsNotValidException {
        if (user == null) {
            throw new UserIsNotValidException("user is null");
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new UserIsNotValidException("users login or password is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserIsNotValidException("user is already registered");
        }
        if (user.getAge() < USER_AGE || user.getPassword().length() < PASSWORD_LENGTH) {
            if (user.getAge() < USER_AGE) {
                throw new UserIsNotValidException(
                        "incorrect age. User should have more than 18 year old");
            } else {
                throw new UserIsNotValidException(
                        "incorrect password. Password should have more than 6 elements");
            }
        }
        storageDao.add(user);
        return user;
    }
}
