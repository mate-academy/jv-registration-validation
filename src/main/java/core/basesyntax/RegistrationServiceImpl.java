package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserIsNotValidException {
        userIsNull(user);
        userLoginOrPasswordIsNull(user);
        userIsAlreadyRegister(user);
        userPasswordIsIncorrect(user);
        userAgeIsIncorrect(user);
        storageDao.add(user);
        return user;
    }

    private void userIsNull(User user) {
        if (user == null) {
            throw new UserIsNotValidException("user is null");
        }
    }

    private void userLoginOrPasswordIsNull(User user) {
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new UserIsNotValidException("users login or password is null");
        }
    }

    private void userPasswordIsIncorrect(User user) {
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new UserIsNotValidException(
                    "incorrect password. Password should have more than 6 elements");
        }
    }

    private void userIsAlreadyRegister(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserIsNotValidException("user is already registered");
        }
    }

    private void userAgeIsIncorrect(User user) {
        if (user.getAge() < USER_AGE) {
            throw new UserIsNotValidException(
                    "incorrect age. User should have more than 18 year old");
        }
    }
}
