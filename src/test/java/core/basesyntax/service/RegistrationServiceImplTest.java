package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RestrictionException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_sameUserAlreadyExists_notOk() throws RestrictionException {
        User existingUser = new User("existingUser", "existingPassword");
        storageDao.add(existingUser);

        User userToRegister = new User("existingUser", "password");
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userToRegister);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        User userWithNullLogin = new User((String) null);

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void register_loginLengthIsThreeCharacters_notOk() {
        User userWithLoginOfThree = new User("usr", "password");

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithLoginOfThree);
        });
    }

    @Test
    void register_loginLengthIsFiveCharacters_notOk() {
        User userWithLoginOfFive = new User("abcde", "password");

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithLoginOfFive);
        });
    }

    @Test
    void register_loginLengthIsSixCharacters_Ok() {
        User userWithLoginOfSix = new User("abcdef", "password");
        boolean actual = userWithLoginOfSix.getLogin().length() == 6;
        assertTrue(actual);
    }

    @Test
    void register_loginLengthIsEightCharacters_Ok() {
        User userWithLoginOfEight = new User("abcdefgh", "password");
        boolean actual = userWithLoginOfEight.getLogin().length() == 8;
        assertTrue(actual);
    }

    @Test
    void register_passwordIsNull_notOk() {
        User userWithNullPassword = new User((String) null);

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithNullPassword);
        });
    }

    @Test
    void register_passwordLengthIsThreeCharacters_notOk() throws RestrictionException {
        User userWithPasswordOfThree = new User("userWithShortPassword", "123");

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithPasswordOfThree);
        });
    }

    @Test
    void register_passwordLengthIsFiveCharacters_notOk() throws RestrictionException {
        User userWithPasswordOfFive = new User("userWithShortPassword", "12345");

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithPasswordOfFive);
        });
    }

    @Test
    void register_passwordLengthIsSixCharacters_Ok() throws RestrictionException {
        User userWithPasswordOfSix = new User("userWithShortPassword", "123456");
        boolean actual = userWithPasswordOfSix.getPassword().length() == 6;
        assertTrue(actual);
    }

    @Test
    void register_passwordLengthIsEightCharacters_Ok() throws RestrictionException {
        User userWithPasswordOfEight = new User("userWithShortPassword", "12345678");
        boolean actual = userWithPasswordOfEight.getPassword().length() == 8;
        assertTrue(actual);
    }

    @Test
    void register_ageIsNull_notOk() {
        User userWithAgeNull = new User("UserWithAgeNull", 0);

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithAgeNull);
        });
    }

    @Test
    void register_ageIsTwelve_notOk() {
        User userWithAgeTwelve = new User("UserWithAgeTwelve", "password", 12);

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithAgeTwelve);
        });
    }

    @Test
    void register_ageIsSeventeen_notOk() {
        User userWithAgeSeventeen = new User("UserWithAgeSeventeen", "password", 17);

        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithAgeSeventeen);
        });
    }

    @Test
    void register_ageIsEighteen_Ok() {
        User userWithAgeEighteen = new User("UserWithAgeEighteen", "password", 18);
        boolean actual = userWithAgeEighteen.getAge() == 18;
        assertTrue(actual);
    }

    @Test
    void register_ageIsNineteen_Ok() {
        User userWithAgeNineteen = new User("UserWithAgeEighteen", "password", 19);
        boolean actual = userWithAgeNineteen.getAge() == 19;
        assertTrue(actual);
    }

    private void addUserToStorage(String login, String password) {
        storageDao.add(new User(login, password));
    }
}
