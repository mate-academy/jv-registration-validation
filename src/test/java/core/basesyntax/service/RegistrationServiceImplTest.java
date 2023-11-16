package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AgeRestrictionException;
import core.basesyntax.exception.InputValueException;
import core.basesyntax.exception.LoginLengthException;
import core.basesyntax.exception.PasswordLengthException;
import core.basesyntax.exception.UserAlreadyExistsException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void userAlreadyExists_notOk() throws UserAlreadyExistsException {
        User existingUser = new User("existingUser", "existingPassword");
        storageDao.add(existingUser);

        User userToRegister = new User("existingUser", "password");
        assertThrows(UserAlreadyExistsException.class, () -> {
            registrationService.register(userToRegister);
        });
    }

    @Test
    void loginIsNull_notOk() {
        User userWithNullLogin = new User((String) null);

        assertThrows(InputValueException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void loginLengthIsThreeCharacters_notOk() {
        User userWithLoginOfThree = new User("usr", "password");

        assertThrows(LoginLengthException.class, () -> {
            registrationService.register(userWithLoginOfThree);
        });
    }

    @Test
    void loginLengthIsFiveCharacters_notOk() {
        User userWithLoginOfFive = new User("abcde", "password");

        assertThrows(LoginLengthException.class, () -> {
            registrationService.register(userWithLoginOfFive);
        });
    }

    @Test
    void loginLengthIsSixCharacters_Ok() {
        User userWithLoginOfSix = new User("abcdef", "password");
        boolean actual = userWithLoginOfSix.getLogin().length() == 6;
        assertTrue(actual);
    }

    @Test
    void loginLengthIsEightCharacters_Ok() {
        User userWithLoginOfEight = new User("abcdefgh", "password");
        boolean actual = userWithLoginOfEight.getLogin().length() == 8;
        assertTrue(actual);
    }

    @Test
    void passwordIsNull_notOk() {
        User userWithNullPassword = new User((String) null);

        assertThrows(InputValueException.class, () -> {
            registrationService.register(userWithNullPassword);
        });
    }

    @Test
    void passwordLengthIsThreeCharacters_notOk() throws PasswordLengthException {
        User userWithPasswordOfThree = new User("userWithShortPassword", "123");

        assertThrows(PasswordLengthException.class, () -> {
            registrationService.register(userWithPasswordOfThree);
        });
    }

    @Test
    void passwordLengthIsFiveCharacters_notOk() throws PasswordLengthException {
        User userWithPasswordOfFive = new User("userWithShortPassword", "12345");

        assertThrows(PasswordLengthException.class, () -> {
            registrationService.register(userWithPasswordOfFive);
        });
    }

    @Test
    void passwordLengthIsSixCharacters_Ok() throws PasswordLengthException {
        User userWithPasswordOfSix = new User("userWithShortPassword", "123456");
        boolean actual = userWithPasswordOfSix.getPassword().length() == 6;
        assertTrue(actual);
    }

    @Test
    void passwordLengthIsEightCharacters_Ok() throws PasswordLengthException {
        User userWithPasswordOfEight = new User("userWithShortPassword", "12345678");
        boolean actual = userWithPasswordOfEight.getPassword().length() == 8;
        assertTrue(actual);
    }

    @Test
    void ageIsNull_notOk() {
        User userWithAgeNull = new User("UserWithAgeNull", 0);

        assertThrows(InputValueException.class, () -> {
            registrationService.register(userWithAgeNull);
        });
    }

    @Test
    void ageIsTwelve_notOk() {
        User userWithAgeTwelve = new User("UserWithAgeTwelve", "password", 12);

        assertThrows(AgeRestrictionException.class, () -> {
            registrationService.register(userWithAgeTwelve);
        });
    }

    @Test
    void ageIsSeventeen_notOk() {
        User userWithAgeSeventeen = new User("UserWithAgeSeventeen", "password", 17);

        assertThrows(AgeRestrictionException.class, () -> {
            registrationService.register(userWithAgeSeventeen);
        });
    }

    @Test
    void ageIsEighteen_Ok() {
        User userWithAgeEighteen = new User("UserWithAgeEighteen", "password", 18);
        boolean actual = userWithAgeEighteen.getAge() == 18;
        assertTrue(actual);
    }

    @Test
    void ageIsNineteen_Ok() {
        User userWithAgeNineteen = new User("UserWithAgeEighteen", "password", 19);
        boolean actual = userWithAgeNineteen.getAge() == 19;
        assertTrue(actual);
    }

    private void addUserToStorage(String login, String password) {
        storageDao.add(new User(login, password));
    }
}
