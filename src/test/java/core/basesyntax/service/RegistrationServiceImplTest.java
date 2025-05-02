package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static User user2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(1L, "first_user", "password1", 21);
        user2 = new User(2L, "second_user", "password2", 22);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Existing user must not be registered");
    }

    @Test
    void register_notExistingUser_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_multipleUsers_ok() {
        assertEquals(user, registrationService.register(user));
        assertEquals(user2, registrationService.register(user2));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "Exception should be thrown for null value");
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with empty login must not be registered");
    }

    @Test
    void register_loginWithFewerChars_notOk() {
        user.setLogin("a");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with less than allowed chars in Login must not be registered");
        user2.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user2),
                "User with fever chars that allowed in Login must not be registered");
    }

    @Test
    void register_loginWithExactChars_ok() {
        user.setLogin("abcdef");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_longLogin_ok() {
        user.setLogin("abcdefg");
        assertEquals(user, registrationService.register(user));
        user2.setLogin("abcdefghjkl123456");
        assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with empty password must not be registered");
    }

    @Test
    void register_passwordWithFewerChars_notOk() {
        user.setPassword("1");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with less than allowed chars in Password must not be registered");
        user2.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user2),
                "User with fever chars that allowed in Password must not be registered");
    }

    @Test
    void register_passwordWithExactChars_ok() {
        user.setPassword("123456");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_longPassword_ok() {
        user.setPassword("1234567890");
        assertEquals(user, registrationService.register(user));
        user2.setPassword("asdfgh123456qwerty567890");
        assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with Password = null must not be registered");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with Login = null must not be registered");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with age = null must not be registered");
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with negative age must not be registered");
    }

    @Test
    void register_ageFewerThanAllowed_notOk() {
        user.setAge(5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with age less than allowed must not be registered");
        user2.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2),
                "User with age less than allowed must not be registered");
    }

    @Test
    void register_minAllowedAge_ok() {
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_ageMoreThanMinAllowed_ok() {
        user.setAge(19);
        assertEquals(user, registrationService.register(user));
        user2.setAge(32);
        assertEquals(user2, registrationService.register(user2));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
