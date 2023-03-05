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
    private static final int initialAge = 24;
    private static final String initialLogin = "IvanIvan";
    private static final String defaultLogin2 = "Misha Hrynko";
    private static final String defaultLogin3 = "Bohdan Chupika";
    private static final String initialPassword = "qwerty1234";
    private static final String incorrectPassword = "12345";
    private static final int incorrectAge = 15;
    private static final int negativeAge = -5;
    private static RegistrationService registrationService;
    private User defaultUser = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser.setAge(initialAge);
        defaultUser.setLogin(initialLogin);
        defaultUser.setPassword(initialPassword);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_correctUser_Ok() {
        registrationService.register(defaultUser);
        assertEquals(defaultUser, Storage.people.get(0));
    }

    @Test
    void register_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_ageLess18_NotOk() {
        defaultUser.setAge(incorrectAge);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_incorrectPassword_notOk() {
        defaultUser.setPassword(incorrectPassword);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_userAlreadyExists_notOk() {
        registrationService.register(defaultUser);
        User user2 = new User();
        user2.setLogin(defaultLogin2);
        user2.setAge(initialAge);
        user2.setPassword(initialPassword);
        registrationService.register(user2);
        User user3 = new User();
        user3.setLogin(defaultLogin3);
        user3.setAge(initialAge);
        user3.setPassword(initialPassword);
        registrationService.register(user3);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(defaultUser);
        });
    }
}
