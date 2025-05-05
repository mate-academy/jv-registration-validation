package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationValidationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EXCEPTION_MESSAGE
            = RegistrationValidationException.class.toString();
    private static final String DEFAULT_LOGIN = "abc";
    private static final String MIN_LIMIT_OF_CHARACTERS_IN_PASSWORD = "abcdef";
    private static final String MAX_LIMIT_OF_CHARACTERS_IN__PASSWORD = "abcdefghijklmnopqrstuvwxyz";
    private static final Integer MIN_AGE = 18;
    private static final Integer MAX_AGE = 90;
    private static RegistrationService registrationService;

    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(MIN_LIMIT_OF_CHARACTERS_IN_PASSWORD);
        user.setPassword(MAX_LIMIT_OF_CHARACTERS_IN__PASSWORD);
        user.setAge(MIN_AGE);
        user.setAge(MAX_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(null),
                String.format("%s should be thrown for user is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for login is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_existingLogin_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for already existing login: "
                        + DEFAULT_LOGIN, EXCEPTION_MESSAGE));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for password is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_invalidMinPasswordLength_notOk() {
        user.setPassword("abcd");
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for invalid password: "
                        + user.getPassword(), EXCEPTION_MESSAGE));
    }

    @Test
    void register_invalidMaxPasswordLength_notOk() {
        user.setPassword("abcdefghijklmnopqrstuvwxyzabc");
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for invalid password: "
                        + user.getPassword(), EXCEPTION_MESSAGE));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for age is null", EXCEPTION_MESSAGE));
    }

    @Test
    void register_invalidMinAge_notOk() {
        user.setAge(9);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for a invalid age: "
                        + user.getAge(), EXCEPTION_MESSAGE));
    }

    @Test
    void register_invalidMaxAge_notOk() {
        user.setAge(91);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown for a invalid age: "
                + user.getAge(), EXCEPTION_MESSAGE));
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(25);
        assertEquals(user, registrationService.register(user));
        assertNotNull(user.getId());
    }

    @Test
    void register_validPassword_Ok() {
        user.setPassword("abcdefghi");
        assertEquals(user, registrationService.register(user));
        assertNotNull(user.getId());
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user),
                "User must be registered");
        assertNotNull(user.getId());
    }
}
