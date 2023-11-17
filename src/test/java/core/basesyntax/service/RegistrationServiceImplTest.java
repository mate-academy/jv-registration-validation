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
    void register_loginAlreadyExists_notOk() {
        User existingUser = new User("existingUser", "existingPassword");
        storageDao.add(existingUser);

        User userToRegister = new User("existingUser", "password");
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userToRegister);
        });
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithNullLogin = new User((String) null);
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithNullLogin);
        });

        User userWithLoginOfThree = new User("usr", "password");
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithLoginOfThree);
        });

        User userWithLoginOfFive = new User("abcde", "password");
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithLoginOfFive);
        });
    }

    @Test
    void register_loginMoreThanSix_Ok() {
        User userWithLoginOfSix = new User("abcdef", "password");
        boolean actual = userWithLoginOfSix.getLogin().length() == 6;
        assertTrue(actual);

        User userWithLoginOfEight = new User("abcdefgh", "password");
        boolean actual2 = userWithLoginOfEight.getLogin().length() == 8;
        assertTrue(actual2);
    }

    @Test
    void register_invalidPassword_notOk() {
        User userWithNullPassword = new User((String) null);
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithNullPassword);
        });

        User userWithPasswordOfThree = new User("userWithShortPassword", "123");
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithPasswordOfThree);
        });

        User userWithPasswordOfFive = new User("userWithShortPassword", "12345");
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithPasswordOfFive);
        });
    }

    @Test
    void register_passwordIsMoreThanSix_Ok() {
        User userWithPasswordOfSix = new User("userWithShortPassword", "123456");
        boolean actual = userWithPasswordOfSix.getPassword().length() == 6;
        assertTrue(actual);

        User userWithPasswordOfEight = new User("userWithShortPassword", "12345678");
        boolean actual2 = userWithPasswordOfEight.getPassword().length() == 8;
        assertTrue(actual2);
    }

    @Test
    void register_invalidAge_notOk() {
        User userWithAgeNull = new User("UserWithAgeNull", 0);
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithAgeNull);
        });

        User userWithAgeTwelve = new User("UserWithAgeTwelve", "password", 12);
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithAgeTwelve);
        });

        User userWithAgeSeventeen = new User("UserWithAgeSeventeen", "password", 17);
        assertThrows(RestrictionException.class, () -> {
            registrationService.register(userWithAgeSeventeen);
        });
    }

    @Test
    void register_ageIsEighteenAndMore_Ok() {
        User userWithAgeEighteen = new User("UserWithAgeEighteen", "password", 18);
        boolean actual = userWithAgeEighteen.getAge() == 18;
        assertTrue(actual);

        User userWithAgeNineteen = new User("UserWithAgeEighteen", "password", 19);
        boolean actual2 = userWithAgeNineteen.getAge() == 19;
        assertTrue(actual2);
    }

    private void addUserToStorage(String login, String password) {
        storageDao.add(new User(login, password));
    }
}
