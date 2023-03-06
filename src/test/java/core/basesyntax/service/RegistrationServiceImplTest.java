package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INITIAL_AGE = 24;
    private static final String INITIAL_LOGIN = "IvanIvan";
    private static final String INITIAL_PASSWORD = "qwerty1234";
    private static final String INCORRECT_PASSWORD = "12345";
    private static final int INCORRECT_AGE = 15;
    private static final int NEGATIVE_AGE = -5;
    private static RegistrationService registrationService;
    private User defaultUser = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser.setAge(INITIAL_AGE);
        defaultUser.setLogin(INITIAL_LOGIN);
        defaultUser.setPassword(INITIAL_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_zeroLoginLength_NotOk() {
        defaultUser.setLogin("");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_correctUser_Ok() {
        registrationService.register(defaultUser);
        assertEquals(defaultUser, Storage.people.get(0));
    }

    @Test
    void register_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_ageLess18_NotOk() {
        defaultUser.setAge(INCORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_incorrectPassword_notOk() {
        defaultUser.setPassword(INCORRECT_PASSWORD);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_negativeAge_NotOk() {
        defaultUser.setAge(NEGATIVE_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_userAlreadyExists_notOk() {
        registrationService.register(defaultUser);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }
}
