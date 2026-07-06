package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void register_validUser_ok() {
        User user = createUser("Yehoria", "YehoriaPasswords", 20);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_existingLogin_notOk() {
        User user = createUser("Yehoria", "YehoriaPasswords", 20);
        new StorageDaoImpl().add(user);

        User another = createUser("Yehoria", "anotherPassword", 25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(another));
    }

    @Test
    void register_shortLoginZeroLength_notOk() {
        User user = createUser("", "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginThreeLength_notOk() {
        User user = createUser("abc", "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginFiveLength_notOk() {
        User user = createUser("abcde", "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = createUser("abcdef", "password", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_loginLengthEight_ok() {
        User user = createUser("abcdefgh", "password", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordZeroLength_notOk() {
        User user = createUser("Yehoria", "", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordThreeLength_notOk() {
        User user = createUser("Yehoria", "abc", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordFiveLength_notOk() {
        User user = createUser("Yehoria", "abcde", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = createUser("Yehoria", "abcdef", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthEight_ok() {
        User user = createUser("Yehoria", "abcdefgh", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = createUser("Yehoria", "password", 17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageEquals18_ok() {
        User user = createUser("Yehoria", "password", 18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createUser("Yehoria", "password", -1);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser("Yehoria", null, 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser("Yehoria", "password", null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
