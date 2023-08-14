package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThrows(RegistrationException.class,
                () -> service.register(new User(null, TEST_PASSWORD, TEST_AGE)));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, null, TEST_AGE)));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, TEST_PASSWORD, null)));
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
        assertThrows(RegistrationException.class,
                () -> service.register(new User(DEFAULT_LOGIN, TEST_PASSWORD, TEST_AGE)));
    }

    @Test
    void register_loginLessThanSixCharacters_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User("", TEST_PASSWORD, TEST_AGE)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User("abc", TEST_PASSWORD, TEST_AGE)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User("abcde", TEST_PASSWORD, TEST_AGE)));
    }

    @Test
    void register_loginAtLeastSixCharacters_ok() {
        User userLoginSixChars = new User("abcdef", TEST_PASSWORD, TEST_AGE);
        User userLoginSevenChars = new User("abcdefg", TEST_PASSWORD, TEST_AGE);
        service.register(userLoginSixChars);
        service.register(userLoginSevenChars);
        assertTrue(Storage.people.contains(userLoginSixChars));
        assertTrue(Storage.people.contains(userLoginSevenChars));
    }

    @Test
    void register_passwordLessThanSixCharacters_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, "", TEST_AGE)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, "abc", TEST_AGE)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, "abcde", TEST_AGE)));
    }

    @Test
    void register_passwordAtLeastSixCharacters_ok() {
        User userPasswordSixChars = new User(TEST_LOGIN, "abcdef", TEST_AGE);
        User userPasswordSevenChars = new User(TEST_LOGIN_1, "abcdefg", TEST_AGE);
        service.register(userPasswordSixChars);
        service.register(userPasswordSevenChars);
        assertTrue(Storage.people.contains(userPasswordSixChars));
        assertTrue(Storage.people.contains(userPasswordSevenChars));
    }

    @Test
    void register_ageNotPositive_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, TEST_PASSWORD, -1)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, TEST_PASSWORD, 0)));
    }

    @Test
    void register_ageUnderEighteen_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, TEST_PASSWORD, 1)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(TEST_LOGIN, TEST_PASSWORD, 17)));
    }

    @Test
    void register_ageAtLeastEighteen_ok() {
        User userAgeEighteen = new User(TEST_LOGIN, TEST_PASSWORD, 18);
        User userAgeNineteen = new User(TEST_LOGIN_1, TEST_PASSWORD, 19);
        service.register(userAgeEighteen);
        service.register(userAgeNineteen);
        assertTrue(Storage.people.contains(userAgeEighteen));
        assertTrue(Storage.people.contains(userAgeNineteen));
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
