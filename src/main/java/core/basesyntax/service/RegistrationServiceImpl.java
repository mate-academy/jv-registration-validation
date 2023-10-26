package core.basesyntax.service;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.cases.of.exceptions.NullExceptionDuringRegistration;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_YEAR = 18;
    private static final int MIN_LENGTH = 6;
    private static final String USER_NULL = "The user/password/age can't be Null";
    private static final String USER_EXISTS = "User exists";
    private static final String AGE_UNDER_18 = "Age - under " + MIN_YEAR + " years old";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "Login and password must contain "
            + "at least " + MIN_LENGTH + " characters";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullAgePasswordLogin(user);
        checkLengthPasswordLoginNotLessSixCharacter(user);
        checkAgeNotMoreEighteen(user.getAge());
        userExist(user);
        return storageDao.add(user);
    }

    public void userExist(User inputUser) {
        User existingUser = storageDao.get(inputUser.getLogin());
        if (existingUser != null && existingUser.equals(inputUser)) {
            throw new ExceptionDuringRegistration(USER_EXISTS);
        }
    }

    public void checkAgeNotMoreEighteen(Integer age) {
        if (age < MIN_YEAR) {
            throw new ExceptionDuringRegistration(AGE_UNDER_18);
        }
    }

    public void checkLengthPasswordLoginNotLessSixCharacter(User user) {
        if (user.getLogin().length() < MIN_LENGTH
                || user.getPassword().length() < MIN_LENGTH) {
            throw new ExceptionDuringRegistration(INCORRECT_LOGIN_OR_PASSWORD);
        }
    }

    public void checkNullAgePasswordLogin(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new NullExceptionDuringRegistration(USER_NULL);
        }
    }
}
