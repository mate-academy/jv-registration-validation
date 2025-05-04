package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int TEST_AGE = 22;
    private static final String DEFAULT_LOGIN = "defaultLogin";
    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_LOGIN_1 = "testLogin1";
    private static final String TEST_PASSWORD = "testPassword";
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, TEST_PASSWORD, TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(TEST_LOGIN, null, TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(TEST_LOGIN, TEST_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_returnUser_ok() {
        User expected = new User(TEST_LOGIN, TEST_PASSWORD, TEST_AGE);
        User actual = service.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(new User(DEFAULT_LOGIN, TEST_PASSWORD, TEST_AGE));
        User user = new User(DEFAULT_LOGIN, TEST_PASSWORD, TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginIsBlank_notOk() {
        User user = new User("", TEST_PASSWORD, TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginIsFiveCharacters_notOk() {
        User user = new User("abcde", TEST_PASSWORD, TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginIsSixCharacters_ok() {
        User userLoginSixChars = new User("abcdef", TEST_PASSWORD, TEST_AGE);
        service.register(userLoginSixChars);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_loginIsSevenPlusCharacters_ok() {
        User userLoginSevenChars = new User("abcdefg", TEST_PASSWORD, TEST_AGE);
        User userLoginSevenPlusChars = new User("abcdefgh", TEST_PASSWORD, TEST_AGE);
        service.register(userLoginSevenChars);
        service.register(userLoginSevenPlusChars);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_passwordIsBlank_notOk() {
        User user = new User(TEST_LOGIN, "", TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordIsFiveCharacters_notOk() {
        User user = new User(TEST_LOGIN, "abcde", TEST_AGE);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordIsSixCharacters_ok() {
        User user = new User(TEST_LOGIN, "abcdef", TEST_AGE);
        service.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_passwordIsSevenPlusCharacters_ok() {
        User userPasswordSevenChars = new User(TEST_LOGIN, "abcdefg", TEST_AGE);
        User userPasswordSevenPlusChars = new User(TEST_LOGIN_1, "abcdefgh", TEST_AGE);
        service.register(userPasswordSevenChars);
        service.register(userPasswordSevenPlusChars);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_ageIsNegative_notOk() {
        User user = new User(TEST_LOGIN, TEST_PASSWORD, -1);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsZero_notOk() {
        User user = new User(TEST_LOGIN, TEST_PASSWORD, 0);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_agePositiveUnderEighteen_notOk() {
        User user = new User(TEST_LOGIN, TEST_PASSWORD, 17);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsEighteen_ok() {
        User user = new User(TEST_LOGIN, TEST_PASSWORD, 18);
        service.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_ageIsNineteenPlus_ok() {
        User userAgeNineteen = new User(TEST_LOGIN, TEST_PASSWORD, 19);
        User userAgeNineteenPlus = new User(TEST_LOGIN_1, TEST_PASSWORD, 25);
        service.register(userAgeNineteen);
        service.register(userAgeNineteenPlus);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_sizeAfterRegistration() {
        List<User> expected = new ArrayList<>();
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User(TEST_LOGIN + (i + 1), TEST_PASSWORD, TEST_AGE);
            expected.add(user);
            service.register(user);
        }
        assertEquals(expected.size(), Storage.people.size());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
