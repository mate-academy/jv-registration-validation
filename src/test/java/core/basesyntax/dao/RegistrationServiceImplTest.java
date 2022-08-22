package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "UserLogin";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final int DEFAULT_AGE = 20;
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setPassword(DEFAULT_PASSWORD);
        validUser.setAge(DEFAULT_AGE);
        validUser.setLogin(DEFAULT_LOGIN);
    }

    @Test
    void register_ValidUser_OK() {
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void age_UnderMinAge_NotOK() {
        validUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void age_UpperMinAge_OK() {
        validUser.setAge(19);
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void age_EqualsMinAge_OK() {
        validUser.setAge(18);
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void age_isNull_NotOK() {
        validUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void age_isNegative_NotOK() {
        validUser.setAge(-18);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void login_IsEmpty_NotOK() {
        validUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void login_IsNull_NotOK() {
        validUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_usersWithSameLogins_NotOK() {
        User newUser = new User();
        newUser.setLogin("UserLogin");
        newUser.setAge(96);
        newUser.setPassword("123456789");
        registrationService.register(validUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void login_Valid_OK() {
        validUser.setLogin("User2");
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void password_twoCharacters_NotOK() {
        validUser.setPassword("ab");
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void password_Valid_OK() {
        validUser.setPassword("1234567");
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void password_isEmpty_NotOK() {
        validUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void password_isNull_NotOK() {
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser));
    }

    @Test
    void user_isNull_NotOK() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
