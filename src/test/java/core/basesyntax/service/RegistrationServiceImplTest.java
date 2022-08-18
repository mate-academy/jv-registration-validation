package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String DEFAULT_LOGIN = "login";
    public static final String DEFAULT_PASSWORD = "password";
    public static final int DEFAULT_AGE = 30;
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin(DEFAULT_LOGIN);
        validUser.setPassword(DEFAULT_PASSWORD);
        validUser.setAge(DEFAULT_AGE);
    }

    @Test
    void register_validData_Ok() {
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertNotNull(validUser.getId());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_emptyLogin_NotOk() {
        validUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_usersWithSameLogins_NotOk() {
        registrationService.register(validUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_lessThenMinAge_NotOk() {
        validUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_moreThenMinAge_Ok() {
        validUser.setAge(19);
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertEquals(validUser.getAge(), actualUser.getAge());
    }

    @Test
    void register_equalToMinAge_Ok() {
        validUser.setAge(18);
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertEquals(validUser.getAge(), actualUser.getAge());
    }

    @Test
    void register_negativeAge_NotOk() {
        validUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_lessThanMinLengthPassword_NotOk() {
        validUser.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_equalToMinLengthPassword_Ok() {
        validUser.setPassword("pass12");
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertEquals(validUser.getPassword(), actualUser.getPassword());
    }

    @Test
    void register_moreThanMinLengthPassword_Ok() {
        validUser.setPassword("pass123");
        User actualUser = registrationService.register(validUser);
        assertEquals(validUser, actualUser);
        assertEquals(validUser.getPassword(), actualUser.getPassword());
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        validUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
